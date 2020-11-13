package com.kiwihouse.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kiwihouse.common.bean.Code;
import com.kiwihouse.common.utils.TimeUtil;
import com.kiwihouse.domain.vo.Response;
import com.kiwihouse.dto.ThresholdDto;
import com.kiwihouse.mapper.FireThresholdMapper;
import com.kiwihouse.util.ResultUtil;
import com.kiwihouse.vo.entire.Result;
import com.kiwihouse.vo.entire.ResultList;
import com.kiwihouse.vo.kiwihouse.ThresholdUpdateVo;

/**
 * @author yjzn
 * @date 2020-03-17-下午 5:39
 */
@Service
public class FireThresholdService {

    @Autowired
    FireThresholdMapper fireThresholdMapper;

    public Response updateInfo(ThresholdUpdateVo updateVo) {

        ThresholdUpdateVo thresholdUpdateVo = fireThresholdMapper.queryInfo(updateVo.getEqptSn());
        if(null==thresholdUpdateVo){
            updateVo.setAddTime(TimeUtil.getCurrentTime());
            Integer row = fireThresholdMapper.addInfo(updateVo);
            if (null == row || row == 0) {
            	return new Response().Success(Code.ADD_FAIL.getCode(), Code.ADD_FAIL.getMsg());
            } else {
            	return new Response().Success(Code.ADD_SUCCESS.getCode(), Code.ADD_SUCCESS.getMsg());
            }
        }else {
            updateVo.setUpdateTime(TimeUtil.getCurrentTime());
            Integer row = fireThresholdMapper.updateInfo(updateVo);
            if (null == row || row == 0) {
            	return new Response().Success(Code.UPDATE_FAIL.getCode(), Code.UPDATE_FAIL.getMsg());
            } else {
            	return new Response().Success(Code.UPDATE_SUCCESS.getCode(), Code.UPDATE_SUCCESS.getMsg());
            }
        }
    }

    /**
     * 查询预警阀值
     * @param eqptSn
     * @return
     */
    public Response queryInfo(String imei) {
        HashMap<String,Object> map = new HashMap<>();
        map.put("imei",imei);
        List<ThresholdDto> list = fireThresholdMapper.query(map);
        Integer row = fireThresholdMapper.queryRow(map);
        if (list.isEmpty()) {
        	return new Response().Success(Code.QUERY_NULL.getCode(), Code.QUERY_NULL.getMsg());
        } else {
            if (null == row || row == 0) {
            	return new Response().Success(Code.EXECUTION_ERROR.getCode(), Code.EXECUTION_ERROR.getMsg());
            } else {
            	return new Response().Success(Code.QUERY_SUCCESS.getCode(), Code.QUERY_SUCCESS.getMsg()).addData("data", list);
            }
        }
    }
}
