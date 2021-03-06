package com.kiwihouse.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.kiwihouse.common.bean.Code;
import com.kiwihouse.common.utils.TimeUtil;
import com.kiwihouse.dao.mapper.ThreePhaseMeasureMapper;
import com.kiwihouse.dao.mapper.ThreePhasePowerMapper;
import com.kiwihouse.domain.vo.Response;
import com.kiwihouse.dto.ThreePhase.ThreePhaseMeasureDto;
import com.kiwihouse.dto.ThreePhase.ThreePhasePowerDao;
import com.kiwihouse.dto.ThreePhase.ThreePhasePowerDto;
import com.kiwihouse.service.ThreePhaseService;
import com.kiwihouse.util.ResultUtil;
import com.kiwihouse.vo.entire.Result;
import com.kiwihouse.vo.entire.ResultList;
import com.kiwihouse.vo.kiwihouse.ReportedQueryVo;
import com.kiwihouse.vo.kiwihouse.ThreePhaseVo;
@Service
public class ThreePhaseServiceImpl implements ThreePhaseService{
	 @Autowired
	    ThreePhaseMeasureMapper threePhaseMeasureMapper;
	    @Autowired
	    ThreePhasePowerMapper threePhasePowerMapper;

	    /**
	     * 查询三相信息
	     *
	     * @return 三相列表
	     */
	    @Override
	    public Response getMaxPowerList(ThreePhaseVo threePhaseVo) {

	        if (!threePhaseVo.verifyDataType()) {
	            return new Response().Fail(Code.VERIFY_FAIL, "dataType参数不正确");
	        }

	        List<String> dateList = null;
	        List<ThreePhasePowerDto> ret = new ArrayList<>();

	        List<ThreePhasePowerDao> list = threePhasePowerMapper.getMaxPower(threePhaseVo);
	        if (list.size() == 0) {
	            return new Response().Success(Code.QUERY_NULL, Code.QUERY_NULL.getMsg());
	        }
	        String dataBeginTime = list.get(0).getAddTime();
	        String dataEndTime = list.get(list.size() - 1).getAddTime();
	        switch (threePhaseVo.getDataType()) {
	            case "day":
	                dateList = TimeUtil.getDayList(threePhaseVo.getBeginTime(), threePhaseVo.getEndTime(), dataBeginTime, dataEndTime);
	                break;
//	            case "min":
//	                dateList = TimeUtil.getMinLists(threePhaseVo.getBeginTime(), threePhaseVo.getEndTime(), dataBeginTime, dataEndTime);
//	                break;
	            case "hour":
	                dateList = TimeUtil.getHourLists(threePhaseVo.getBeginTime(), threePhaseVo.getEndTime(), dataBeginTime, dataEndTime);
	                break;
	        }
	        List<ThreePhasePowerDto> tmpList = new ArrayList<>();

	        list.forEach(x -> {
	            JSONArray pwrA = JSONArray.parseArray(x.getPwrA());
	            JSONArray pwrB = JSONArray.parseArray(x.getPwrB());
	            JSONArray pwrC = JSONArray.parseArray(x.getPwrC());
	            List<String> minList = TimeUtil.getMinList(x.getAddTime(), x.getNum(), true);
	            for (int i = 0; i < x.getNum(); i++) {
	                double ta = Math.abs(pwrA.getDoubleValue(i));
	                double tb = Math.abs(pwrB.getDoubleValue(i));
	                double tc = Math.abs(pwrC.getDoubleValue(i));
	                double power = ta + tb + tc;
	                tmpList.add(new ThreePhasePowerDto()
	                        .setImei(threePhaseVo.getImei())
	                        .setPwrA(ta)
	                        .setPwrB(tb)
	                        .setPwrC(tc)
	                        .setMaxPower(power)
	                        .setAddTime(minList.get(i))
	                );
	            }
	        });
	        if (threePhaseVo.getDataType().equals("min")) {
	        	if (tmpList.isEmpty()) {
	                return new Response().Success(Code.QUERY_NULL, Code.QUERY_NULL.getMsg());
	            } else {
	                if (tmpList.size() == 0) {
	                	return new Response().Success(Code.EXECUTION_ERROR, Code.EXECUTION_ERROR.getMsg());
	                } else {
	                	return new Response().Success(Code.QUERY_SUCCESS, Code.QUERY_SUCCESS.getMsg()).addData("data", list);
	                }
	            }
	        }
	        for (String x : dateList) {
	            double maxPower = 0;
	            ThreePhasePowerDto item = null;
	            List<ThreePhasePowerDto> tmp = tmpList.stream().filter(y -> y.getAddTime().startsWith(x)).collect(Collectors.toList());

	            for (ThreePhasePowerDto dao : tmp) {
	                if (maxPower < dao.getMaxPower()) {
	                    maxPower = dao.getMaxPower();
	                    item = dao;
	                }
	            }
	            if (item != null) {
	                ret.add(item);
	            }
	        }
	        if (ret.isEmpty()) {
	        	return new Response().Success(Code.QUERY_NULL, Code.QUERY_NULL.getMsg());
	        } else {
	            if (ret.size() == 0) {
	            	return new Response().Success(Code.EXECUTION_ERROR, Code.EXECUTION_ERROR.getMsg());
	            } else {
	            	return new Response().Success(Code.QUERY_SUCCESS, Code.QUERY_SUCCESS.getMsg()).addData("data", list);
	            }
	        }
	    }

	    /**
	     * 获取三相设备最新状态
	     *
	     * @param threePhaseVo
	     * @return
	     */
	    @Override
	    public Response getLastStatus(ReportedQueryVo queryVo) {
//	        if(!userInfo.isAdmin()){
//	            queryVo.setAdminId("");
//	            queryVo.setUserId(userInfo.getUserId());
//	        }
	        ThreePhaseMeasureDto dto = threePhaseMeasureMapper.getLastStatus(queryVo.getImei());
	        if(dto == null) {
	        	return new Response().Success(Code.QUERY_NULL, Code.QUERY_NULL.getMsg());
	        }
	        return new Response().Success(Code.QUERY_SUCCESS, Code.QUERY_SUCCESS.getMsg()).addData("data", dto);
	    }

	    /**
	     * 查询四类上报信息(测量数据,告警信息,运行数据,设置参数)
	     *
	     * @param reportedQueryVo 查询参数
	     * @return 上报信息
	     */
	    public ResultList queryInfo(ReportedQueryVo reportedQueryVo) {
	        return null;
	    }
}
