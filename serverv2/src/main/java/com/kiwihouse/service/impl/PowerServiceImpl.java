package com.kiwihouse.service.impl;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
import com.kiwihouse.dao.entity.OnePhseDataAndRealPwr;
import com.kiwihouse.dao.entity.ThreePhseDataAndRealPwr;
import com.kiwihouse.dao.mapper.DevHistoryDateMapper;
import com.kiwihouse.dao.mapper.EquipmentMapper;
import com.kiwihouse.domain.vo.Response;
import com.kiwihouse.dto.EqptInfoDto;
import com.kiwihouse.service.PowerService;
import com.kiwihouse.util.excel.DateUtils;
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
		DevHistoryDateStatistics devHistoryDateStatistics = new DevHistoryDateStatistics();
		List<Float>  pwr = new ArrayList<Float>();
		List<Float>  pwr2 = new ArrayList<Float>();
		List<String> addTime = new ArrayList<String>();
//		List<Float>  curA = new ArrayList<Float>();
//		List<Float>  curB = new ArrayList<Float>();
//		List<Float>  curC = new ArrayList<Float>();
		List<Float>  pwrTotle = new ArrayList<Float>();
		List<Float>  pwrTotle2 = new ArrayList<Float>();
		List<Float>  pwrA = new ArrayList<Float>();
		List<Float>  pwrB = new ArrayList<Float>();
		List<Float>  pwrC = new ArrayList<Float>();
//		List<Float>  volA = new ArrayList<Float>();
//		List<Float>  volB = new ArrayList<Float>();
//		List<Float>  volC = new ArrayList<Float>();
		List<Float>  curRidevol = new ArrayList<Float>();
//		List<Float>  pwrFctA = new ArrayList<Float>();
//		List<Float>  pwrFctB = new ArrayList<Float>();
//		List<Float>  pwrFctC = new ArrayList<Float>();
		reportedQueryVo.getType();
		list = devHistoryDateMapper.queryPwr(reportedQueryVo);
		Map<String,List<OnePhseDataAndRealPwr>> opdarMap = new HashMap<String, List<OnePhseDataAndRealPwr>>();
		Map<String,List<ThreePhseDataAndRealPwr>> tpdarMap = new HashMap<String, List<ThreePhseDataAndRealPwr>>();
		if(DataType.ONE_PHASE.toString().equals(eqptInfo.getEqptType())) {
			list.forEach(x ->{
				JSONObject jo = JSON.parseObject(x.getDataJson());
				if(jo != null) {
					Float c = jo.getFloat("cur");
					Float v = jo.getFloat("vol");
					Float p = jo.getFloat("pwr");
					OnePhseDataAndRealPwr darp = new OnePhseDataAndRealPwr(c * v ,p,DateUtils.dateTime("yyyy-MM-dd HH:mm:ss",x.getAddTime()));
					curRidevol.add(c * v);
					pwr.add(p);
					addTime.add(x.getAddTime());
					if(opdarMap.containsKey(dateFormat(reportedQueryVo.getType(),x.getAddTime()))) {
						List<OnePhseDataAndRealPwr> listF = opdarMap.get(dateFormat(reportedQueryVo.getType(),x.getAddTime()));
						listF.add(darp);
						opdarMap.put(dateFormat(reportedQueryVo.getType(),x.getAddTime()), listF);
					}else {
						List<OnePhseDataAndRealPwr> listF = new ArrayList<OnePhseDataAndRealPwr>();
						listF.add(darp);
						opdarMap.put(dateFormat(reportedQueryVo.getType(),x.getAddTime()), listF);
					}
				}
			});
			if(pwr.size() > 0) {
				pwr2.addAll(pwr);
				Collections.sort(pwr);
				Float minPwr = Collections.min(pwr);
				int minIndex = returnIndex(pwr2, minPwr);
				String minDate = addTime.get(minIndex);
				pwr.clear();
				addTime.clear();
				curRidevol.clear();
				List<OnePhseDataAndRealPwr> darpList =new ArrayList<OnePhseDataAndRealPwr>();
				for(Map.Entry<String, List<OnePhseDataAndRealPwr>> entry : opdarMap.entrySet()){
				    if(dateFormat(reportedQueryVo.getType(),minDate).equals(entry.getKey())) {
				    	OnePhseDataAndRealPwr onePhseDataAndRealPwr = returnOPDAR(entry.getValue(),minPwr);
				    	onePhseDataAndRealPwr.setAddTime(DateUtils.dateTime("yyyy-MM-dd HH:mm:ss", minDate));
				    	darpList.add(onePhseDataAndRealPwr);
				    }else {
				    	OnePhseDataAndRealPwr onePhseDataAndRealPwr = returnOPDARMax(entry.getValue());
				    	darpList.add(onePhseDataAndRealPwr);
				    }
				}
				Collections.sort(darpList,new Comparator<OnePhseDataAndRealPwr>(){
					@Override
					public int compare(OnePhseDataAndRealPwr o1, OnePhseDataAndRealPwr o2) {
						// TODO Auto-generated method stub
						return o1.getAddTime().compareTo(o2.getAddTime());
					}
				});
				darpList.forEach(xx ->{
					System.out.println(xx);
					pwr.add(xx.getDataPwr());
					curRidevol.add(xx.getRealPwr());
					addTime.add(DateUtils.parseDateToStr(dateFormatType(null), xx.getAddTime()));
				});
				devHistoryDateStatistics.setPwr(pwr);
				devHistoryDateStatistics.setCurRidevol(curRidevol);
				devHistoryDateStatistics.setAddTime(addTime);
			}
		}else if(DataType.THREE_PHASE.toString().equals(eqptInfo.getEqptType())) {
			list.forEach(x ->{
				JSONObject jo = JSON.parseObject(x.getDataJson());
				if(jo != null) {
					addTime.add(x.getAddTime());
					JSONArray curs = JSON.parseArray(jo.getString("cur"));
//					JSONArray vols = JSON.parseArray(jo.getString("vol"));
					JSONArray pwrs = JSON.parseArray(jo.getString("pwr"));
//					JSONArray pwrFcts = JSON.parseArray(jo.getString("pwr_fact"));
					Float pwrAF = 0.0f;
					Float pwrBF = 0.0f;
					Float pwrCF = 0.0f;
					Float totle = 0.0f;
					for(int i = 0;i< curs.size()  ; i++) {
						totle += pwrs.getFloatValue(i);
						if(i == 0) {
//							curA.add(curs.getFloatValue(i));
							pwrA.add(pwrs.getFloatValue(i));
//							volA.add(vols.getFloatValue(i));
//							pwrFctA.add(pwrFcts.getFloatValue(i));
							pwrAF = pwrs.getFloatValue(i);
						}else if(i == 1) {
//							curB.add(curs.getFloatValue(i));
							pwrB.add(pwrs.getFloatValue(i));
//							volB.add(vols.getFloatValue(i));
//							pwrFctB.add(pwrFcts.getFloatValue(i));
							pwrBF = pwrs.getFloatValue(i);
						}else if(i==2) {
//							curC.add(curs.getFloatValue(i));
							pwrC.add(pwrs.getFloatValue(i));
//							volC.add(vols.getFloatValue(i));
//							pwrFctC.add(pwrFcts.getFloatValue(i));
							pwrCF = pwrs.getFloatValue(i);
						}
					}
					pwrTotle.add(totle);
					ThreePhseDataAndRealPwr tpdrp =	new ThreePhseDataAndRealPwr(DateUtils.dateTime("yyyy-MM-dd HH:mm:ss", x.getAddTime()), pwrAF, pwrBF, pwrCF, totle);
					if(tpdarMap.containsKey(dateFormat(reportedQueryVo.getType(),x.getAddTime()))) {
						List<ThreePhseDataAndRealPwr> listF = tpdarMap.get(dateFormat(reportedQueryVo.getType(),x.getAddTime()));
						listF.add(tpdrp);
						tpdarMap.put(dateFormat(reportedQueryVo.getType(),x.getAddTime()), listF);
					}else {
						List<ThreePhseDataAndRealPwr> listF = new ArrayList<ThreePhseDataAndRealPwr>();
						listF.add(tpdrp);
						tpdarMap.put(dateFormat(reportedQueryVo.getType(),x.getAddTime()), listF);
					}
				}
			});
			if(pwrTotle.size() > 0) {
				pwrTotle2.addAll(pwrTotle);
				Collections.sort(pwrTotle);
				Float minPwrTotle = Collections.min(pwrTotle);
				int index = returnIndex(pwrTotle2,minPwrTotle);
				String minDate = addTime.get(index);
				pwrTotle.clear();
				pwrA.clear();
				pwrB.clear();
				pwrC.clear();
				addTime.clear();
				List<ThreePhseDataAndRealPwr> threePhseDataAndRealPwrList = new ArrayList<ThreePhseDataAndRealPwr>();
				for(Map.Entry<String, List<ThreePhseDataAndRealPwr>> entry : tpdarMap.entrySet()){
				    if(dateFormat(reportedQueryVo.getType(),minDate).equals(entry.getKey())) {
				    	ThreePhseDataAndRealPwr threePhseDataAndRealPwr = returnTPDAR(entry.getValue(),minPwrTotle);
				    	threePhseDataAndRealPwr.setAddTime(DateUtils.dateTime("yyyy-MM-dd HH:mm:ss", minDate));
				    	threePhseDataAndRealPwrList.add(threePhseDataAndRealPwr);
				    }else {
				    	ThreePhseDataAndRealPwr threePhseDataAndRealPwr = returnTPDARMax(entry.getValue());
				    	threePhseDataAndRealPwrList.add(threePhseDataAndRealPwr);
				    }
				}
				Collections.sort(threePhseDataAndRealPwrList,new Comparator<ThreePhseDataAndRealPwr>(){
					@Override
					public int compare(ThreePhseDataAndRealPwr o1, ThreePhseDataAndRealPwr o2) {
						// TODO Auto-generated method stub
						return o1.getAddTime().compareTo(o2.getAddTime());
					}
				});
				threePhseDataAndRealPwrList.forEach(xx ->{
					pwrTotle.add(xx.getPwrTotle());
					pwrA.add(xx.getPwrA());
					pwrB.add(xx.getPwrB());
					pwrC.add(xx.getPwrC());
					addTime.add(DateUtils.parseDateToStr("yyyy-MM-dd HH:mm:ss", xx.getAddTime()));
			    });
//				devHistoryDateStatistics.setCurA(curA);
//				devHistoryDateStatistics.setCurB(curB);
//				devHistoryDateStatistics.setCurC(curC);
//				devHistoryDateStatistics.setPwrFctA(pwrFctA);
//				devHistoryDateStatistics.setPwrFctB(pwrFctB);
//				devHistoryDateStatistics.setPwrFctC(pwrFctC);
//				devHistoryDateStatistics.setVolA(volA);
//				devHistoryDateStatistics.setVolB(volB);
//				devHistoryDateStatistics.setVolC(volC);
				devHistoryDateStatistics.setPwrTotle(pwrTotle);
				devHistoryDateStatistics.setPwrA(pwrA);
				devHistoryDateStatistics.setPwrB(pwrB);
				devHistoryDateStatistics.setPwrC(pwrC);
				devHistoryDateStatistics.setAddTime(addTime);
			}
		}
		return new Response().Success(Code.QUERY_SUCCESS,Code.QUERY_SUCCESS.getMsg()).addData("data", devHistoryDateStatistics);
	}
	/**
	 * 	根据查询参数（天、小时、分钟） 决定时间格式化
	 * @param type
	 * @param date
	 * @return
	 */
	public static String dateFormat(String type,String date) {
		if("day".equals(type)) {
			return date.substring(0, 10) + " 00:00:00";
		}else if("hour".equals(type)) {
			return date.substring(0, 13)+ ":00:00";
		}
		
		return date;
	}
	/**
	 * 	根据Value 查询数组下标
	 * @param list
	 * @param o
	 * @return
	 */
	public static int returnIndex(List<Float> list,Float o) {
		int index = 0;
		for(int i = 0;i<list.size();i++) {
			if(list.get(i) == o) {
				index = i;
			}
		}
		return index;
		
	}
	
	/**
	 * 	根据下标查询最大功率最小值javaBean对象
	 * @param threePhseDataAndRealPwrList
	 * @param minPwrTotle
	 * @return threePhseDataAndRealPwr
	 */
	public static ThreePhseDataAndRealPwr returnTPDAR(List<ThreePhseDataAndRealPwr> threePhseDataAndRealPwrList ,Float minPwrTotle) {
		ThreePhseDataAndRealPwr threePhseDataAndRealPwr = new ThreePhseDataAndRealPwr();
		for(int i = 0; i < threePhseDataAndRealPwrList.size() ;i++) {
			if(threePhseDataAndRealPwrList.get(i).getPwrTotle() == minPwrTotle) {
				threePhseDataAndRealPwr = threePhseDataAndRealPwrList.get(i);
			}
		}
		return threePhseDataAndRealPwr;
		
	}
	/**
	 * 	根据下标查询最大功率最小值javaBean对象
	 * @param threePhseDataAndRealPwrList
	 * @param minPwrTotle
	 * @return onePhseDataAndRealPwr
	 */
	public static OnePhseDataAndRealPwr returnOPDAR(List<OnePhseDataAndRealPwr> onePhseDataAndRealPwrList ,Float minPwr) {
		OnePhseDataAndRealPwr onePhseDataAndRealPwr = new OnePhseDataAndRealPwr();
		for(int i = 0; i < onePhseDataAndRealPwrList.size() ;i++) {
			if(onePhseDataAndRealPwrList.get(i).getDataPwr() == minPwr) {
				onePhseDataAndRealPwr = onePhseDataAndRealPwrList.get(i);
			}
		}
		return onePhseDataAndRealPwr;
		
	}
	/**
	 * 	查询最大值(三相)
	 * @param threePhseDataAndRealPwrList
	 * @return
	 */
	public static ThreePhseDataAndRealPwr returnTPDARMax(List<ThreePhseDataAndRealPwr> threePhseDataAndRealPwrList) {
		Collections.sort(threePhseDataAndRealPwrList,new Comparator<ThreePhseDataAndRealPwr>() {
			@Override
			public int compare(ThreePhseDataAndRealPwr o1, ThreePhseDataAndRealPwr o2) {
				return o2.getPwrTotle().compareTo(o1.getPwrTotle());
			}
		});
		return threePhseDataAndRealPwrList.get(0);
	}
	/**
	 * 	查询最大值(单相)
	 * @param onePhseDataAndRealPwrList
	 * @return
	 */
	public static OnePhseDataAndRealPwr returnOPDARMax(List<OnePhseDataAndRealPwr> onePhseDataAndRealPwrList) {
		Collections.sort(onePhseDataAndRealPwrList,new Comparator<OnePhseDataAndRealPwr>() {
			@Override
			public int compare(OnePhseDataAndRealPwr o1, OnePhseDataAndRealPwr o2) {
				return o2.getDataPwr().compareTo(o1.getDataPwr());
			}
		});
		return onePhseDataAndRealPwrList.get(0);
	}
	/**
	 * 	根据类型  决定时间转化格式
	 * @param type
	 * @return
	 */
	public static String dateFormatType(String type) {
		if("day".equals(type)) {
			return "yyyy-MM-dd";
		}else if("hour".equals(type)) {
			return "yyyy-MM-dd HH";
		}
		return "yyyy-MM-dd HH:mm:ss";
	}
	
	
}
