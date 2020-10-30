package com.kiwihouse.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kiwihouse.common.bean.AlmSta;
import com.kiwihouse.common.bean.Code;
import com.kiwihouse.common.bean.DataType;
import com.kiwihouse.common.bean.MtSta;
import com.kiwihouse.common.utils.TimeUtil;
import com.kiwihouse.dao.entity.AuthUser;
import com.kiwihouse.dao.entity.IMEI;
import com.kiwihouse.dao.mapper.AlarmMapper;
import com.kiwihouse.dao.mapper.AuthRoleMapper;
import com.kiwihouse.dao.mapper.AuthUserMapper;
import com.kiwihouse.dao.mapper.EquipmentMapper;
import com.kiwihouse.dao.mapper.MaintainMapper;
import com.kiwihouse.domain.vo.Response;
import com.kiwihouse.dto.MtInfoDto;
import com.kiwihouse.dto.MtSmokeInfoDto;
import com.kiwihouse.service.MaintainService;
import com.kiwihouse.vo.entire.Result;
import com.kiwihouse.vo.entire.ResultList;
import com.kiwihouse.vo.kiwihouse.MtInfoVo;
import com.kiwihouse.vo.kiwihouse.MtUpdateVo;

@Service
public class MaintainServiceImpl implements MaintainService{

	 	@Autowired
	    MaintainMapper maintainMapper;

	 	@Autowired
	 	AlarmMapper AlarmMapper;
	 	@Autowired
	 	EquipmentMapper equipmentMapper;
	 	@Autowired
	 	AuthRoleMapper authRoleMapper;
	 	@Autowired
	 	AuthUserMapper authUserMapper;
	    /**
	     * 查询:告警信息+维修记录
	     * @param mtInfoVo 查询参数
	     * @return 维修信息结果集
	     */
	 	@Override
	    public ResultList queryInfo(MtInfoVo mtInfoVo) {


	 		String mtStatus = mtInfoVo.getMtStatus();
	        if("9".equals(mtStatus)){
	            mtInfoVo.setMtStatus("noCancel");
	        }

	        if("2".equals(mtInfoVo.getMtType())){
	            //查询烟感设备工单
	            List<MtSmokeInfoDto> list = maintainMapper.querySmokeInfo(mtInfoVo);
	            if(list.isEmpty()){
	                return new ResultList(Code.QUERY_NULL.getCode(),Code.QUERY_NULL.getMsg(),null);
	            }else{
	                Integer row = maintainMapper.querySmokeInfoRow(mtInfoVo);
	                if(row==0){
	                    return new ResultList(Code.EXECUTION_ERROR.getCode(),Code.EXECUTION_ERROR.getMsg(),null);
	                }else{
	                    list.forEach(mtSmokeInfoDto -> mtSmokeInfoDto.setMtType("2"));
	                    return new ResultList(Code.QUERY_SUCCESS.getCode(), Code.QUERY_SUCCESS.getMsg(), new Result<>(row, list));
	                }
	            }

	        }else{
	        	//获取角色的设备IMEI号
	        	List<IMEI> imeiList = equipmentMapper.selectUserImei(Integer.valueOf(mtInfoVo.getRoleId()));
	            //查询用户信息  判断是否是最大管理员
//	        	AuthRole authRole =  authRoleMapper.selectIsAdmin(mtInfoVo.getUserId());
	        	AuthUser auth = authUserMapper.selectByPrimaryKey(mtInfoVo.getUserId());
	        	List<MtInfoDto> list = null;
	        	if(auth.getUsername().equals(DataType.admin)) {
	        		list = maintainMapper.queryInfo(mtInfoVo,null);
	    		}else {
	    			list = maintainMapper.queryInfo(mtInfoVo,imeiList);
	    		}
	            if(list.isEmpty()){
	                return new ResultList(Code.QUERY_NULL.getCode(),Code.QUERY_NULL.getMsg(),null);
	            }else{
	                Integer row = maintainMapper.queryInfoRow(mtInfoVo,imeiList);
	                if(row==0){
	                    return new ResultList(Code.EXECUTION_ERROR.getCode(),Code.EXECUTION_ERROR.getMsg(),null);
	                }else{
	                    list.forEach(mtInfoDto -> {
	                        mtInfoDto.setMtType("1");
	                        System.out.println(mtInfoDto.toString());
	                        if("0".equals(mtInfoDto.getEqptType())) {
	                        	mtInfoDto.setAlarmMsg(ReportedInfoServiceImpl.onePhaseAlarm(mtInfoDto.getAlarmMsg()));
	                        }else if("1".equals(mtInfoDto.getEqptType())) {
	                        	mtInfoDto.setAlarmMsg(ReportedInfoServiceImpl.threePhaseAlarm(mtInfoDto.getAlarmMsg()));
	                        }
	                    });
	                    return new ResultList(Code.QUERY_SUCCESS.getCode(), Code.QUERY_SUCCESS.getMsg(), new Result<>(row, list));
	                }
	            }
	        }
	    }

	    /**
	     * 告警信息转为工单
	     * @param alarmId 录入信息
	     * @return 成功与否
	     */
	    @Transactional(rollbackFor = Exception.class)
	    @Override
	    public Response addInfo(String alarmId,String imei,String mtType) {

	        Integer almRow;
	        Integer row;
	        if(DataType.ONE_PHASE.toString().equals(mtType) || DataType.THREE_PHASE.toString().equals(mtType)){
	            //用电设备
	            almRow = AlarmMapper.updateAlmSta(alarmId, AlmSta.TO_ORDER.getCode());
	            row = maintainMapper.addInfo(alarmId, imei, TimeUtil.getCurrentTime());
	        }else if ("3".equals(mtType)) {
	        	almRow = maintainMapper.updateSmokeAlmSta(alarmId, AlmSta.TO_ORDER.getCode());
	            row = maintainMapper.addSmokeInfo(alarmId, imei, TimeUtil.getCurrentTime());
	        }else{
	        	return new Response().Fail(Code.PARAM_FORMAT_ERROR, Code.PARAM_FORMAT_ERROR.getMsg());
	        }

	        if (row == 0 && almRow == 0) {
	        	return new Response().Fail(Code.ADD_FAIL, Code.ADD_FAIL.getMsg());
	        } else {
	        	return new Response().Success(Code.ADD_SUCCESS, Code.ADD_SUCCESS.getMsg());
	        }
	    }

	    /**
	     * 维修人员处理告警结束，录入信息并修改告警处理状态
	     * 根据状态修改告警信息对应的状态
	     * @param mtUpdateVo 更新参数
	     * @return 成功与否
	     */
	    @Transactional(rollbackFor = Exception.class)
	    @Override
	    public Response updateInfo(MtUpdateVo mtUpdateVo) {
	        String mtType = mtUpdateVo.getMtType();
	        if("1".equals(mtType) || "2".equals(mtType)) {
	            Integer mtRow = maintainMapper.updateMtInfo(mtUpdateVo);

	            // 默认为1，则当不修改状态时，也可以返回处理成功
	            Integer almRow = 1;
	            String mtStatus = mtUpdateVo.getMtStatus();
	            if (MtSta.UNPROCESSED.getCode().equals(mtStatus)) {
	                //未维修 对应 已转为工单
	                almRow = AlarmMapper.updateAlmSta(mtUpdateVo.getAlarmId(), AlmSta.TO_ORDER.getCode());
	            } else if (MtSta.PROCESSED.getCode().equals(mtStatus)) {
	                //已维修 对应 已处理
	                almRow = AlarmMapper.updateAlmSta(mtUpdateVo.getAlarmId(), AlmSta.PROCESSED.getCode());
	            } else if (MtSta.CANCEL.getCode().equals(mtStatus)) {
	                //撤销订单 对应 错误告警
	                almRow = AlarmMapper.updateAlmSta(mtUpdateVo.getAlarmId(), AlmSta.ERROR.getCode());
	            }

	            if (almRow == 1 && mtRow == 1) {
	            	return new Response().Success(Code.UPDATE_SUCCESS, Code.UPDATE_SUCCESS.getMsg());
	            } else {
	            	return new Response().Fail(Code.UPDATE_FAIL, Code.UPDATE_FAIL.getMsg());
	            }
	        }else if("3".equals(mtType)){
	            Integer mtRow = maintainMapper.updateMtSmokeInfo(mtUpdateVo);

	            // 默认为1，则当不修改状态时，也可以返回处理成功
	            Integer almRow = 1;
	            String mtStatus = mtUpdateVo.getMtStatus();
	            if (MtSta.UNPROCESSED.getCode().equals(mtStatus)) {
	                //未维修 对应 已转为工单
	                almRow = maintainMapper.updateSmokeAlmSta(mtUpdateVo.getAlarmId(), AlmSta.TO_ORDER.getCode());
	            } else if (MtSta.PROCESSED.getCode().equals(mtStatus)) {
	                //已维修 对应 已处理
	                almRow = maintainMapper.updateSmokeAlmSta(mtUpdateVo.getAlarmId(), AlmSta.PROCESSED.getCode());
	            } else if (MtSta.CANCEL.getCode().equals(mtStatus)) {
	                //撤销订单 对应 错误告警
	                almRow = maintainMapper.updateSmokeAlmSta(mtUpdateVo.getAlarmId(), AlmSta.ERROR.getCode());
	            }

	            if (almRow == 1 && mtRow == 1) {
	            	return new Response().Success(Code.UPDATE_SUCCESS, Code.UPDATE_SUCCESS.getMsg());
	            } else {
	            	return new Response().Fail(Code.UPDATE_FAIL, Code.UPDATE_FAIL.getMsg());
	            }
	        }else{
	        	return new Response().Success(Code.ADD_SUCCESS, Code.ADD_SUCCESS.getMsg());
	        }

	    }

		@Override
		public Response insertOrUpdateBatch(List<MtInfoDto> userList) {
			// TODO Auto-generated method stub
			int count = 0;
//			try {
				count = maintainMapper.insertOrUpdateBatch(userList);
				System.out.println("受影响行数----------------->" + count);
				if(count > 0 ) {
					return new Response().Fail(Code.EXCEL_LEAD_IN_SUCCESS,Code.EXCEL_LEAD_IN_SUCCESS.getMsg());
				}
				return new Response().Fail(Code.EXCEL_LEAD_IN_FAIL,Code.EXCEL_LEAD_IN_FAIL.getMsg());
//			} catch (Exception e) {
//				// TODO: handle exception
//				return new Response().Fail(Code.EXCEL_LEAD_IN_FAIL,Code.EXCEL_LEAD_IN_FAIL.getMsg());
//			}
		}
}
