package com.kiwihouse.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kiwihouse.common.bean.Code;
import com.kiwihouse.common.bean.DataType;
import com.kiwihouse.dao.entity.DevHistoryDate;
import com.kiwihouse.dao.entity.DevHistoryDateStatistics;
import com.kiwihouse.dao.mapper.DevHistoryDateMapper;
import com.kiwihouse.domain.vo.Response;
import com.kiwihouse.service.DevHistoryDateService;
import com.kiwihouse.vo.kiwihouse.ReportedQueryVo;
@Service
public class DevHistoryDateServiceImpl implements DevHistoryDateService{

	@Autowired
	DevHistoryDateMapper devHistoryDateMapper;
	@Override
	public Response historyDevInfo(ReportedQueryVo reportedQueryVo) {
		List<DevHistoryDate> list = null;
		////ThreePhaseMeasureDto  //ImprovedWarnMsgDto
		DevHistoryDateStatistics devHistoryDateStatistics = new DevHistoryDateStatistics();
		List<Float>  cur = new ArrayList<Float>();
		List<Float>  vol = new ArrayList<Float>();
		List<Float>  pwr = new ArrayList<Float>();
		List<Float>  kwh = new ArrayList<Float>();
		List<Float>  lineTemp = new ArrayList<Float>();
		List<Float>  leakCur = new ArrayList<Float>();
		List<Integer> csq = new ArrayList<Integer>();
		List<Float> pwrFct = new ArrayList<Float>();
		List<String> addTime = new ArrayList<String>();
		
		List<Float>  curA = new ArrayList<Float>();
		List<Float>  curB = new ArrayList<Float>();
		List<Float>  curC = new ArrayList<Float>();
		
		List<Float>  pwrA = new ArrayList<Float>();
		List<Float>  pwrB = new ArrayList<Float>();
		List<Float>  pwrC = new ArrayList<Float>();
		
		List<Float>  volA = new ArrayList<Float>();
		List<Float>  volB = new ArrayList<Float>();
		List<Float>  volC = new ArrayList<Float>();
		
		List<Float>  pwrFctA = new ArrayList<Float>();
		List<Float>  pwrFctB = new ArrayList<Float>();
		List<Float>  pwrFctC = new ArrayList<Float>();
		if(DataType.ONE_PHASE.toString().equals(reportedQueryVo.getEqptType())) {
			list = devHistoryDateMapper.historyDevInfo(reportedQueryVo);
			list.forEach(x ->{
				JSONObject jo = JSON.parseObject(x.getDataJson());
				if(jo != null) {
					Float c = jo.getFloat("cur");
					cur.add(c);
					Float v = jo.getFloat("vol");
					vol.add(v);
					Float p = jo.getFloat("pwr");
					pwr.add(p);
					Float k = jo.getFloat("kwh");
					kwh.add(k);
					Float lt = jo.getFloat("line_temp");
					lineTemp.add(lt);
					Float lc = jo.getFloat("leak_cur");
					leakCur.add(lc);
					Integer cs = jo.getInteger("csq");
					csq.add(cs);
					Float pf = jo.getFloat("pwr_fact");
					pwrFct.add(pf);
					addTime.add(x.getAddTime());
				}
			});
			devHistoryDateStatistics.setCsq(csq);
			devHistoryDateStatistics.setCur(cur);
			devHistoryDateStatistics.setKwh(kwh);
			devHistoryDateStatistics.setLeakCur(leakCur);
			devHistoryDateStatistics.setPwr(pwr);
			devHistoryDateStatistics.setPwrFct(pwrFct);
			devHistoryDateStatistics.setVol(vol);
			devHistoryDateStatistics.setAddTime(addTime);
		}else if(DataType.THREE_PHASE.toString().equals(reportedQueryVo.getEqptType())) {
			list = devHistoryDateMapper.historyDevInfo(reportedQueryVo);
			list.forEach(x ->{
				JSONObject jo = JSON.parseObject(x.getDataJson());
				if(jo != null) {
					Float k = jo.getFloat("kwh");
					kwh.add(k);
					Float lt = jo.getFloat("line_temp");
					lineTemp.add(lt);
					Float lc = jo.getFloat("leak_cur");
					leakCur.add(lc);
					Integer cs = jo.getInteger("csq");
					csq.add(cs);
					addTime.add(x.getAddTime());
					
					JSONArray curs = JSON.parseArray(jo.getString("cur"));
					JSONArray vols = JSON.parseArray(jo.getString("vol"));
					JSONArray pwrs = JSON.parseArray(jo.getString("pwr"));
					JSONArray pwrFcts = JSON.parseArray(jo.getString("pwr_fact"));
					for(int i = 0;i< curs.size()  ; i++) {
						if(i == 0) {
							curA.add(curs.getFloatValue(i));
							pwrA.add(pwrs.getFloatValue(i));
							volA.add(vols.getFloatValue(i));
							pwrFctA.add(pwrFcts.getFloatValue(i));
						}else if(i == 1) {
							curB.add(curs.getFloatValue(i));
							pwrB.add(pwrs.getFloatValue(i));
							volB.add(vols.getFloatValue(i));
							pwrFctB.add(pwrFcts.getFloatValue(i));
						}else if(i==2) {
							curC.add(curs.getFloatValue(i));
							pwrC.add(pwrs.getFloatValue(i));
							volC.add(vols.getFloatValue(i));
							pwrFctC.add(pwrFcts.getFloatValue(i));
						}
					}
					
				}
				
			});
			devHistoryDateStatistics.setCsq(csq);
			devHistoryDateStatistics.setCurA(curA);
			devHistoryDateStatistics.setCurB(curB);
			devHistoryDateStatistics.setCurC(curC);
			devHistoryDateStatistics.setKwh(kwh);
			devHistoryDateStatistics.setLeakCur(leakCur);
			devHistoryDateStatistics.setPwrA(pwrA);
			devHistoryDateStatistics.setPwrB(pwrB);
			devHistoryDateStatistics.setPwrC(pwrC);
			devHistoryDateStatistics.setPwrFctA(pwrFctA);
			devHistoryDateStatistics.setPwrFctB(pwrFctB);
			devHistoryDateStatistics.setPwrFctC(pwrFctC);
			devHistoryDateStatistics.setVolA(volA);
			devHistoryDateStatistics.setVolB(volB);
			devHistoryDateStatistics.setVolC(volC);
			devHistoryDateStatistics.setAddTime(addTime);
		}
		return new Response().Success(Code.QUERY_SUCCESS,Code.QUERY_SUCCESS.getMsg()).addData("data", devHistoryDateStatistics);
	}
	@Override
	public Map<String, Object> selectAll(ReportedQueryVo reportedQueryVo) {
		List<DevHistoryDate> list = null;
		Map<String, Object> map = new HashMap<String, Object>();
		// TODO Auto-generated method stub
		try {
			
			list = devHistoryDateMapper.historyDevInfo(reportedQueryVo);
			if("0".equals(reportedQueryVo.getEqptType())) {
				list.forEach(x ->{
					JSONObject jo = JSON.parseObject(x.getDataJson());
					Float c = jo.getFloat("cur");
					x.setCur(c);
					Float v = jo.getFloat("vol");
					x.setVol(v);
					Float p = jo.getFloat("pwr");
					x.setPwr(p);
					Float k = jo.getFloat("kwh");
					x.setKwh(k);
					Float lt = jo.getFloat("line_temp");
					x.setLineTemp(lt);
					Float lc = jo.getFloat("leak_cur");
					x.setLeakCur(lc);
					Integer cs = jo.getInteger("csq");
					x.setCsq(cs);
					Float pf = jo.getFloat("pwr_fct");
					x.setPwrFct(pf);
				});
			}else if("1".equals(reportedQueryVo.getEqptType())) {
				list.forEach(x ->{
					JSONObject jo = JSON.parseObject(x.getDataJson());
					JSONArray curs = JSON.parseArray(jo.getString("cur"));
					JSONArray vols = JSON.parseArray(jo.getString("vol"));
					JSONArray pwrs = JSON.parseArray(jo.getString("pwr"));
					JSONArray pwrFcts = JSON.parseArray(jo.getString("pwr_fact"));
					for(int i = 0;i< curs.size()  ; i++) {
						if(i == 0) {
							x.setCurA(curs.getFloatValue(i));
							x.setVolA(vols.getFloatValue(i));
							x.setPwrA(pwrs.getFloatValue(i));
							x.setPwrFctA(pwrFcts.getFloatValue(i));
						}else if(i == 1) {
							x.setCurB(curs.getFloatValue(i));
							x.setVolB(vols.getFloatValue(i));
							x.setPwrB(pwrs.getFloatValue(i));
							x.setPwrFctB(pwrFcts.getFloatValue(i));
						}else if(i==2) {
							x.setCurC(curs.getFloatValue(i));
							x.setVolC(vols.getFloatValue(i));
							x.setPwrC(pwrs.getFloatValue(i));
							x.setPwrFctC(pwrFcts.getFloatValue(i));
						}
					}
					Float k = jo.getFloat("kwh");
					x.setKwh(k);
					Float lt = jo.getFloat("line_temp");
					x.setLineTemp(lt);
					Float lc = jo.getFloat("leak_cur");
					x.setLeakCur(lc);
					Integer cs = jo.getInteger("csq");
					x.setCsq(cs);
				});
			}
			
			Integer count =  devHistoryDateMapper.historyDevInfoCount(reportedQueryVo);
			map.put("count", count);
			map.put("data", list);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return map;
	}

}
