package com.kiwihouse.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kiwihouse.common.bean.Code;
import com.kiwihouse.common.bean.EqptTypeSta;
import com.kiwihouse.common.bean.UserInfo;
import com.kiwihouse.common.utils.CodeTransferUtil;
import com.kiwihouse.common.utils.GroupList;
import com.kiwihouse.common.utils.RedisUtil;
import com.kiwihouse.dao.entity.RoleDev;
import com.kiwihouse.dao.mapper.EquipmentMapper;
import com.kiwihouse.domain.vo.Response;
import com.kiwihouse.dto.Eqpt4UpdateDto;
import com.kiwihouse.dto.EqptInfoDto;
import com.kiwihouse.dto.SiteDto;
import com.kiwihouse.service.CommandIssueService;
import com.kiwihouse.service.EquipmentService;
import com.kiwihouse.vo.kiwihouse.EqptAddVo;
import com.kiwihouse.vo.kiwihouse.EqptQueryVo;

@Service
public class EquipmentServiceImpl implements EquipmentService {

	@Autowired
	EquipmentMapper equipmentMapper;

	@Autowired
	CommandIssueService commandIssueService;
	
	@Autowired
	RedisUtil redisUtil;

	@Override
	public Map<String, Object> queryInfo(EqptQueryVo eqptQueryVo) {

		Map<String, Object> map = new HashMap<String, Object>();
		List<EqptInfoDto> list = new ArrayList<EqptInfoDto>();
		Integer limit = eqptQueryVo.getLimit();
		Integer page = eqptQueryVo.getPage();
		if (StringUtils.isNotBlank(eqptQueryVo.getOnline())) {// 如果查询设备状态
			eqptQueryVo.setLimit(null);
			list = equipmentMapper.querInfoByUserIdPage(eqptQueryVo);
			list.forEach(eqpt -> {
				eqpt.setEqptStatus(String.valueOf(Code.NOTONLINE.getCode()));
                  if (redisUtil.hasKey(eqpt.getImei())) {
                	  eqpt.setEqptStatus(String.valueOf(Code.ONLINE.getCode()));
                  }
			});
			List<EqptInfoDto> collect;
			if ("1".equals(eqptQueryVo.getOnline())) {
				collect = list.stream().filter(
						eqptInfoDto -> eqptInfoDto.getEqptStatus().equals(String.valueOf(Code.ONLINE.getCode())))
						.collect(Collectors.toList());
			} else if ("-1".equals(eqptQueryVo.getOnline())) {
				collect = list.stream().filter(
						eqptInfoDto -> eqptInfoDto.getEqptStatus().equals(String.valueOf(Code.NOTONLINE.getCode())))
						.collect(Collectors.toList());
			} else {
				collect = list;
			}
			// 手动处理分页
			HashMap<Integer, List> maps = GroupList.groupList(collect, limit);
			map.put("data", maps.get(page - 1));
			map.put("msg", Code.QUERY_SUCCESS.getMsg());
			map.put("count", collect.size());
			map.put("code", 0);
		} else {
			if(limit!=null) {
				eqptQueryVo.setPage(limit * (page - 1));
				eqptQueryVo.setLimit(limit);
			}
			list = equipmentMapper.querInfoByUserIdPage(eqptQueryVo);
			list.forEach(eqpt -> {
				eqpt.setEqptStatus(String.valueOf(Code.NOTONLINE.getCode()));
				if (redisUtil.hasKey(eqpt.getImei())) {
					eqpt.setEqptStatus(String.valueOf(Code.ONLINE.getCode()));
				}
			});
			int count = equipmentMapper.queryInfoCount(eqptQueryVo);
			map.put("data", list);
			map.put("msg", Code.QUERY_SUCCESS.getMsg());
			map.put("count", count);
			map.put("code", 0);
		}
		return map;
	}

	/**
     * 	更新设备信息
     *
     * @param updateDto 更新设备信息参数对象
     * @return 是否更新成功
     */
	@Override
    public Response updateInfo(Eqpt4UpdateDto updateDto) {
        //1.如果code存在，判断该区域是否已存在，存在则将设备siteId改为该siteId，不存在则录入地址然后将设备siteId改为该新录入的siteId
        Eqpt4UpdateDto eqpt4UpdateDto = addSite(updateDto);
        Integer row = equipmentMapper.updateInfo(eqpt4UpdateDto);
        if (null == row || row == 0) {
        	return new Response().Success(Code.UPDATE_FAIL.getCode(), Code.UPDATE_FAIL.getMsg());
        } else {
        	return new Response().Success(Code.UPDATE_SUCCESS.getCode(), Code.UPDATE_SUCCESS.getMsg());
        }
    }

    /**
     * 	录入设备信息(应该是设备生产完成，入库的时候使用)
     * @param eqptAddVo 录入设备信息参数
     * @return 是否录入成功
     */
	@Override
    @Transactional(rollbackFor = Exception.class)
    public Response addInfo(EqptAddVo eqptAddVo) {
		if (eqptAddVo.getVoltage() != null && eqptAddVo.getVoltage() .equals("220")) {
            eqptAddVo.setEqptType(EqptTypeSta.DX);
        } else if (eqptAddVo.getVoltage()  != null && eqptAddVo.getVoltage() .equals("380")) {
            eqptAddVo.setEqptType(EqptTypeSta.SX);
        }
        Integer row = equipmentMapper.addInfo(eqptAddVo);
        if (row == 0) {
        	return new Response().Success(Code.ADD_FAIL.getCode(), Code.ADD_FAIL.getMsg());
        } else {
        	equipmentMapper.addRoleDev(eqptAddVo.getEqptId(),eqptAddVo.getRoleId());
        	return new Response().Success(Code.ADD_SUCCESS.getCode(), Code.ADD_SUCCESS.getMsg());
        }
    }
    private Eqpt4UpdateDto addSite(Eqpt4UpdateDto updateDto) {
        //code不为空添加设备对应的地址，返回siteId
        if (!StringUtils.isBlank(updateDto.getCode())) {
//            if (StringUtils.isBlank(updateDto.getAdminId()) || StringUtils.isBlank(updateDto.getAddress())) {
//                throw new ParamException(Code.PARAM_FORMAT_ERROR.getCode(), "修改设备地址时必须同时传递详细地址以及adminId");
//            }
            CodeTransferUtil.transferAll(updateDto.getCode(), updateDto);

            SiteDto siteDto = equipmentMapper.querySiteInfoForUpdate(updateDto);
            if (null == siteDto) {
                equipmentMapper.addSiteForUpdate(updateDto);
            } else {
                updateDto.setSiteId(siteDto.getSiteId());
            }
        }
        return updateDto;
    }

    /**
     * 删除设备信息
     *
     * @param eqptSn 设备序列号
     * @return 是否删除成功
     */
    @Override
    public Response deleteInfo(String imeis, UserInfo userInfo) {
    	String [] imeiArr = imeis.split("_");
    	if (equipmentMapper.deleteBatch(imeiArr) > 0) {
    		return new Response().Success(Code.DELETE_SUCCESS.getCode(), Code.DELETE_SUCCESS.getMsg());
        }
        return new Response().Success(Code.DELETE_FAIL.getCode(), Code.DELETE_FAIL.getMsg());

    }

	@Override
	public Response selectOneInfo(EqptQueryVo eqptQueryVo) {
		// TODO Auto-generated method stub
		try {
			EqptInfoDto eqpt = equipmentMapper.selectOneInfo(eqptQueryVo);
			if(eqpt != null) {
				return new Response().Success(Code.QUERY_SUCCESS.getCode(), Code.QUERY_SUCCESS.getMsg()).addData("data", eqpt);
			}
			return new Response().Success(Code.QUERY_NULL.getCode(), Code.QUERY_NULL.getMsg());
		} catch (Exception e) {
			// TODO: handle exception
			return new Response().Success(Code.QUERY_FAIL.getCode(), Code.QUERY_FAIL.getMsg());
		}
		
		
		
	}

	@Override
	public Response insertOrUpdateBatch(List<EqptInfoDto> userList) {
		// TODO Auto-generated method stub
		int count = 0;
		try {
			count = equipmentMapper.insertOrUpdateBatch(userList);
			return new Response().Success(Code.EXCEL_LEAD_IN_SUCCESS,Code.EXCEL_LEAD_IN_SUCCESS.getMsg());
		} catch (Exception e) {
//			 TODO: handle exception
			return new Response().Fail(Code.EXCEL_LEAD_IN_FAIL,Code.EXCEL_LEAD_IN_FAIL.getMsg());
		}
		
	}

	
	/**
	 * 	查询角色设备列表
	 */
	@Override
	public Map<String, Object> queryRoleDevList(EqptQueryVo eqptQueryVo) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		Integer limit = eqptQueryVo.getLimit();
		Integer page = eqptQueryVo.getPage();
		if(limit!=null) {
			eqptQueryVo.setPage(limit * (page - 1));
			eqptQueryVo.setLimit(limit);
		}
		List<EqptInfoDto> list = equipmentMapper.querInfoByUserIdPage(eqptQueryVo);
		int count = equipmentMapper.queryInfoCount(eqptQueryVo);
		map.put("data", list);
		map.put("msg", Code.QUERY_SUCCESS.getMsg());
		map.put("count", count);
		map.put("code", 0);
		return map;
	}

	@Override
	public Response updateRoleDevList(Integer roleId, String deptIds) {
		// TODO Auto-generated method stub
		String [] deptArr = deptIds.split(",");
		List<RoleDev> roleDevList = new ArrayList<RoleDev>();
        for(int i = 0;i<deptArr.length;i++) {
        	RoleDev roleDev = new RoleDev(roleId,Integer.valueOf(deptArr[i]));
        	roleDevList.add(roleDev);
        }
        equipmentMapper.deleteRoleDev(roleId);
        equipmentMapper.insertRoleDevList(roleDevList);
		return new Response().Success(Code.ADD_SUCCESS,Code.ADD_SUCCESS.getMsg());
	}

}
