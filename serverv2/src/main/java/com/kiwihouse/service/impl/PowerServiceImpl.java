package com.kiwihouse.service.impl;
import java.util.ArrayList;
import java.util.List;
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
import com.kiwihouse.dao.mapper.EquipmentMapper;
import com.kiwihouse.domain.vo.Response;
import com.kiwihouse.dto.EqptInfoDto;
import com.kiwihouse.service.PowerService;
import com.kiwihouse.vo.kiwihouse.ReportedQueryVo;
@Service
public class PowerServiceImpl implements PowerService{
	@Autowired
	EquipmentMapper equipmentMapper;
	@Autowired 
	DevHistoryDateMapper devHistoryDateMapper;
	@Override
	public Response queryPwr(ReportedQueryVo reportedQueryVo) {
		// TODO Auto-generated method stub
		EqptInfoDto eqptInfo = equipmentMapper.queryInfoByImei(reportedQueryVo.getImei());
		List<DevHistoryDate> list = null;
		
		////ThreePhaseMeasureDto  //ImprovedWarnMsgDto
		DevHistoryDateStatistics devHistoryDateStatistics = new DevHistoryDateStatistics();
		List<Float>  pwr = new ArrayList<Float>();
		List<String> addTime = new ArrayList<String>();
		
		List<Float>  curA = new ArrayList<Float>();
		List<Float>  curB = new ArrayList<Float>();
		List<Float>  curC = new ArrayList<Float>();
		List<Float>  pwrTotle = new ArrayList<Float>();
		List<Float>  pwrA = new ArrayList<Float>();
		List<Float>  pwrB = new ArrayList<Float>();
		List<Float>  pwrC = new ArrayList<Float>();
		
		List<Float>  volA = new ArrayList<Float>();
		List<Float>  volB = new ArrayList<Float>();
		List<Float>  volC = new ArrayList<Float>();
		
		List<Float>  curRidevol = new ArrayList<Float>();
		List<Float>  pwrFctA = new ArrayList<Float>();
		List<Float>  pwrFctB = new ArrayList<Float>();
		List<Float>  pwrFctC = new ArrayList<Float>();
		list = devHistoryDateMapper.queryPwr(reportedQueryVo);
//		
//		 String dataBeginTime = list.get(0).getAddTime();
//	        String dataEndTime = list.get(list.size() - 1).getAddTime();
//	        List<String> dateList = null;
//	        switch (reportedQueryVo.getType()) {
//	            case "day":
//	                dateList = TimeUtil.getDayList(reportedQueryVo.getStartTime(), reportedQueryVo.getEndTime(), dataBeginTime, dataEndTime);
//	                break;
//	            case "min":
//	                dateList = TimeUtil.getMinLists(reportedQueryVo.getStartTime(), reportedQueryVo.getEndTime(), dataBeginTime, dataEndTime);
//	                break;
//	            case "hour":
//	                dateList = TimeUtil.getHourLists(reportedQueryVo.getStartTime(), reportedQueryVo.getEndTime(),  dataBeginTime, dataEndTime);
//	                break;
//	        }

//	        List<SinglePhasePowerDto> tmpList = new ArrayList<>();
//	        List<SinglePhasePowerDto> returnList = new ArrayList<>();

//	        list.forEach(x -> {
//	            PwrMsg pwrMsg = JSONObject.parseObject(x.getPwrMsg(), PwrMsg.class);
//	            List<Double> pwrList = pwrMsg.getPwr();
//	            List<String> minList = TimeUtil.getMinList(x.getAddTime(), pwrMsg.getNum(), true);
//	            for (int i = 0; i < pwrMsg.getNum(); i++) {
//	                tmpList.add(new SinglePhasePowerDto()
//	                        .setAddTime(minList.get(i))
//	                        .setPwr(pwrList.get(i)));
//	            }
//	        });
//	        switch (reportedQueryVo.getType()) {
//	            case "hour":
//	            	System.out.println("进入------------>HOUR");
//	            case "day":
//	            	System.out.println("进入------------>DAY");
//	                dateList.forEach(x -> {
//	                    double maxPower = 0;
//	                    SinglePhasePowerDto item = null;
//	                    List<SinglePhasePowerDto> tmp = tmpList.stream().filter(y -> y.getAddTime().startsWith(x)).collect(Collectors.toList());
//	                    for (SinglePhasePowerDto dto : tmp) {
//	                        if (maxPower < dto.getPwr()) {
//	                            maxPower = dto.getPwr();
//	                            item = dto;
//	                        }
//	                    }
//	                    if (item != null) {
//	                        returnList.add(item);
//	                    }
//	                });
//	                return new Response().Success(Code.QUERY_SUCCESS, Code.QUERY_SUCCESS.getMsg()).addData("data", returnList);
//	            case "min":
//	            	System.out.println("进入------------>MIN");
//	            	return new Response().Success(Code.QUERY_SUCCESS, Code.QUERY_SUCCESS.getMsg()).addData("data", tmpList);
//	        }
		
		if(DataType.ONE_PHASE.toString().equals(eqptInfo.getEqptType())) {
			list.forEach(x ->{
				JSONObject jo = JSON.parseObject(x.getDataJson());
				if(jo != null) {
					Float c = jo.getFloat("cur");
					Float v = jo.getFloat("vol");
					Float p = jo.getFloat("pwr");
					pwr.add(p);
					curRidevol.add(c * v);
					addTime.add(x.getAddTime());
				}
			});
			devHistoryDateStatistics.setPwr(pwr);
			devHistoryDateStatistics.setCurRidevol(curRidevol);
			devHistoryDateStatistics.setAddTime(addTime);
			
			
			
		}else if(DataType.THREE_PHASE.toString().equals(eqptInfo.getEqptType())) {
			list.forEach(x ->{
				JSONObject jo = JSON.parseObject(x.getDataJson());
				System.out.println(x.toString());
				if(jo != null) {
					addTime.add(x.getAddTime());
					
					JSONArray curs = JSON.parseArray(jo.getString("cur"));
					JSONArray vols = JSON.parseArray(jo.getString("vol"));
					JSONArray pwrs = JSON.parseArray(jo.getString("pwr"));
					JSONArray pwrFcts = JSON.parseArray(jo.getString("pwr_fact"));
					float totle = 0.0f;
					for(int i = 0;i< curs.size()  ; i++) {
						totle += pwrs.getFloatValue(i);
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
					pwrTotle.add(totle);
				}
				
			});
			devHistoryDateStatistics.setCurA(curA);
			devHistoryDateStatistics.setCurB(curB);
			devHistoryDateStatistics.setCurC(curC);
			devHistoryDateStatistics.setPwrA(pwrA);
			devHistoryDateStatistics.setPwrB(pwrB);
			devHistoryDateStatistics.setPwrC(pwrC);
			devHistoryDateStatistics.setPwrFctA(pwrFctA);
			devHistoryDateStatistics.setPwrFctB(pwrFctB);
			devHistoryDateStatistics.setPwrFctC(pwrFctC);
			devHistoryDateStatistics.setPwrTotle(pwrTotle);
			devHistoryDateStatistics.setVolA(volA);
			devHistoryDateStatistics.setVolB(volB);
			devHistoryDateStatistics.setVolC(volC);
			devHistoryDateStatistics.setAddTime(addTime);
		}
		return new Response().Success(Code.QUERY_SUCCESS,Code.QUERY_SUCCESS.getMsg()).addData("data", devHistoryDateStatistics);
	}
	/**
	 * 数据清洗
	 * @param list
	 * @param type
	 * @return
	 */
//	public List<DevHistoryDate> DataClean(List<DevHistoryDate> list,String type) {
//		HashMap< String, List<DevHistoryDate>> map = new HashMap<String, List<DevHistoryDate>>();
//		if("hour".equals(type)) {
//			for(int i = 0; i < list.size(); i++) {
//				if(i<list.size() - 1) {
//					if(list.get(i).getAddTime().substring(0,13).equals(list.get(i + 1).getAddTime().substring(0,13))) {
//						DevHistoryDate jo = new DevHistoryDate();
//						jo.put("date", list.get(i).getAddTime());
//						jo.put("value", list.get(i).getDataJson());
//						if(map.get(list.get(i).getAddTime().substring(0,13)) == null) {
//							List<DevHistoryDate> l = new ArrayList<DevHistoryDate>();
//							l.add(jo);
//							map.put(list.get(i).getAddTime().substring(0,13), l);
//						}else {
//							List<Object> l = map.get(list.get(i).getAddTime().substring(0,13));
//							if(l.size() > 1) {
//								JSONObject json_01 = JSON.parseObject(l.get(0).toString());
//								JSONObject json_02 = JSON.parseObject(l.get(1).toString());
//								Float pwr_01 = JSON.parseObject(json_01.getString("value")).getFloat("pwr");
//								Float pwr_02 = JSON.parseObject(json_02.getString("value")).getFloat("pwr");
//								Float pwr_03 = JSON.parseObject(list.get(i).getDataJson()).getFloat("pwr");
//								if(pwr_01 > pwr_02) {
//									if(pwr_03 > pwr_02 && pwr_03 > pwr_01) {
//										l.remove(0);
//									}else if(pwr_03 < pwr_02 && pwr_03 < pwr_01) {
//										l.remove(1);
//									}
//								}else if(pwr_01 < pwr_02) {
//									if(pwr_03 > pwr_02 && pwr_03 > pwr_01) {
//										l.remove(1);
//									}else if(pwr_03 < pwr_02 && pwr_03 < pwr_01) {
//										l.remove(0);
//									}
//								}else {
//									l.remove(0);
//								}
//								
//							}else {
//								l.add(jo);
//							}
//							map.put(list.get(i).getAddTime().substring(0,13), l);
//						}
//						
//					}
//				}
//				
//			}
//		}
//		for(Map.Entry<String, String> entry : map.entrySet()){
//		    String mapKey = entry.getKey();
//		    String mapValue = entry.getValue();
//		    System.out.println(mapKey+":"+mapValue);
//		}
//		return null;
//		
//	}
}
