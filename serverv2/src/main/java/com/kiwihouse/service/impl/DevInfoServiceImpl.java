package com.kiwihouse.service.impl;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kiwihouse.common.bean.Code;
import com.kiwihouse.common.bean.DataType;
import com.kiwihouse.dao.entity.DevInfo;
import com.kiwihouse.dao.mapper.DevInfoMapper;
import com.kiwihouse.domain.vo.Response;
import com.kiwihouse.dto.ThreePhase.ThreePhaseMeasureDto;
import com.kiwihouse.service.DevInfoService;
@Service
public class DevInfoServiceImpl implements DevInfoService{
	@Autowired
	DevInfoMapper devInfoMapper;
	@Override
	public Response selectDevByNewTime(String imei,Integer type,Integer eqptType) {
		// TODO Auto-generated method stub
		DevInfo devInfo = devInfoMapper.selectDevByNewTime(imei,type);
		if(devInfo == null) {
			return new Response().Success(Code.QUERY_NULL, Code.QUERY_NULL.getMsg());
		}
		if(eqptType == DataType.ONE_PHASE) {
			
			
			
			
			return new Response().Success(Code.QUERY_SUCCESS, Code.QUERY_SUCCESS.getMsg()).addData("data", devInfo);
		}else if(eqptType == DataType.THREE_PHASE) {
			ThreePhaseMeasureDto threePhaseMeasureDto = new ThreePhaseMeasureDto();
			JSONObject jsonObject = JSON.parseObject(devInfo.getDataJson());
			int csq = (int) jsonObject.get("csq");
			JSONArray cur = (JSONArray) jsonObject.get("cur");
			JSONArray vol = (JSONArray) jsonObject.get("vol");
			JSONArray pwr = (JSONArray) jsonObject.get("pwr");
			JSONArray pwrFact = (JSONArray) jsonObject.get("pwr_fact");
			float hz = Float.valueOf(jsonObject.get("Hz").toString()) ;
			double kwh = Double.valueOf(jsonObject.get("kwh").toString()) ;
			float leakCur = Float.valueOf(jsonObject.get("leak_cur").toString()); 
			double lineTemp = Double.valueOf(jsonObject.get("line_temp").toString()); 
			String lac = jsonObject.get("lac").toString();
			String cid = jsonObject.get("cid").toString();
			for(int i = 0;i<cur.size();i++) {
				if(i == 0) {
					threePhaseMeasureDto.setCurA(Float.valueOf(cur.get(i).toString()));
					threePhaseMeasureDto.setVolA(Float.valueOf(vol.get(i).toString()));
					threePhaseMeasureDto.setPwrA(Float.valueOf(pwr.get(i).toString()));
					threePhaseMeasureDto.setPwrFctA(Float.valueOf(pwrFact.get(i).toString()));
				}else if(i == 1){
					threePhaseMeasureDto.setCurB(Float.valueOf(cur.get(i).toString()));
					threePhaseMeasureDto.setVolB(Float.valueOf(vol.get(i).toString()));
					threePhaseMeasureDto.setPwrB(Float.valueOf(pwr.get(i).toString()));
					threePhaseMeasureDto.setPwrFctB(Float.valueOf(pwrFact.get(i).toString()));
				}else if(i == 2) {
					threePhaseMeasureDto.setCurC(Float.valueOf(cur.get(i).toString()));
					threePhaseMeasureDto.setVolC(Float.valueOf(vol.get(i).toString()));
					threePhaseMeasureDto.setPwrC(Float.valueOf(pwr.get(i).toString()));
					threePhaseMeasureDto.setPwrFctC(Float.valueOf(pwrFact.get(i).toString()));
				}
			}
			System.out.println(cur.toJSONString());
			threePhaseMeasureDto.setCsp(csq);
			threePhaseMeasureDto.setHz(hz);
			threePhaseMeasureDto.setKwh(kwh);
			threePhaseMeasureDto.setLeakCur(leakCur);
			threePhaseMeasureDto.setLac(lac);
			threePhaseMeasureDto.setCid(cid);
			threePhaseMeasureDto.setLineTemp(lineTemp);
			threePhaseMeasureDto.setAddTime(devInfo.getAddTime());
			return new Response().Success(Code.QUERY_SUCCESS, Code.QUERY_SUCCESS.getMsg()).addData("data", threePhaseMeasureDto);
		}
		
		return new Response().Success(Code.QUERY_SUCCESS, Code.QUERY_SUCCESS.getMsg()).addData("data", null);
	}
}
