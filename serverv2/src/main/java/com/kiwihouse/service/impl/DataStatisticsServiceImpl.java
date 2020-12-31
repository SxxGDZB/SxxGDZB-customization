package com.kiwihouse.service.impl;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kiwihouse.common.bean.Code;
import com.kiwihouse.common.bean.DataType;
import com.kiwihouse.common.utils.MapKeyComparator;
import com.kiwihouse.common.utils.RedisUtil;
import com.kiwihouse.common.utils.TimeUtil;
import com.kiwihouse.dao.entity.AuthUser;
import com.kiwihouse.dao.entity.DataList;
import com.kiwihouse.dao.entity.DataTimeNum;
import com.kiwihouse.dao.entity.DateStatis;
import com.kiwihouse.dao.entity.DateStatisList;
import com.kiwihouse.dao.entity.IMEI;
import com.kiwihouse.dao.mapper.AlarmMapper;
import com.kiwihouse.dao.mapper.AuthRoleMapper;
import com.kiwihouse.dao.mapper.AuthUserMapper;
import com.kiwihouse.dao.mapper.DataStatisticsMapper;
import com.kiwihouse.dao.mapper.EquipmentMapper;
import com.kiwihouse.dto.DevAlarmListDto;
import com.kiwihouse.dto.DevStatis;
import com.kiwihouse.dto.EqptInfoDto;
import com.kiwihouse.service.DataStatisticsService;
import com.kiwihouse.util.HttpServletUtils;
import com.kiwihouse.util.Response;
import com.kiwihouse.util.ResponseList;
import com.kiwihouse.vo.entire.Result;
import com.kiwihouse.vo.entire.ResultList;
import com.kiwihouse.vo.kiwihouse.DataStatisticsVo;
import com.kiwihouse.vo.kiwihouse.EqptQueryVo;

@Service
public class DataStatisticsServiceImpl implements DataStatisticsService{

	@Autowired
    DataStatisticsMapper dataStatisticsMapper;
	@Autowired
	EquipmentMapper equipmentMapper;
	@Autowired
	AlarmMapper alarmMapper;
	@Autowired
	AuthRoleMapper authRoleMapper;
	@Autowired
	AuthUserMapper authUserMapper;
	@Autowired
	RedisUtil redisUtil;
    /**
     * 查询数据总览信息
     * @param dataStatisticsVo 查询参数
     * @return
     */
	@Override
	public ResultList queryInfo(DataStatisticsVo dataStatisticsVo) {
		AuthUser authUser = HttpServletUtils.getAuthUser();
		List<Date> list = TimeUtil.getBetweenDates(TimeUtil.strToDate(dataStatisticsVo.getStartTime(),"yyyy-MM-dd"),TimeUtil.strToDate(dataStatisticsVo.getEndTime(),"yyyy-MM-dd"));
		List<String> listTime = TimeUtil.getDayList(dataStatisticsVo.getStartTime(), dataStatisticsVo.getEndTime());
		DateStatisList dateStatisList = new DateStatisList(listTime.size());
		dateStatisList.setAddTime(listTime.toArray(new String[listTime.size()]));
		//获取用户的设备IMEI号
		List<IMEI> imeiList = equipmentMapper.selectUserImei(authUser.getRoleId(),authUser.getUid());
		AuthUser auth = authUserMapper.selectByPrimaryKey(authUser.getUid());
		EqptQueryVo eqptQueryVo = new EqptQueryVo();
    	eqptQueryVo.setEqptType("0");
    	eqptQueryVo.setRoleId(authUser.getRoleId().toString());
    	eqptQueryVo.setUserId(authUser.getUid());
    	int electricAlarms = 0;
    	System.out.println(dateStatisList.toString());
//		for(int i = 1;i<9;i++) {
//        	if(auth.getUsername().equals(DataType.admin)) {
//        		 eqptQueryVo.setAdminId(2);
//        		 electricAlarms = alarmMapper.timeAlarmCount(dataStatisticsVo,"0,1",null);
//        		 List<DataTimeNum> listData = alarmMapper.selectByTimesAndType(dataStatisticsVo,i,null);
//				 Map<String,Integer> mapl = new HashMap<String, Integer>();
//				 List<Map<String,Integer>> strList = new ArrayList<Map<String,Integer>>();
//				 list.forEach(l ->{
//					 mapl.put(TimeUtil.dateToStr(l), 0);
//					 Map<String,Integer> map2 = new HashMap<String, Integer>();
//					 map2.put(TimeUtil.dateToStr(l), 0);
//					 strList.add(map2);
//					 
//				 });
//				 listData.forEach(dataTimeNum ->{
//					 	 mapl.put(dataTimeNum.getAddTime(), dataTimeNum.getNum());
//					 });
//				 if(i == 1) {
//					dateStatisList.setOverCurrentAlarm(dataTo(mapl));
//				 }else if(i == 2) {
//					 dateStatisList.setTemperatureAlarm(dataTo(mapl));
//				 }else if(i == 3) {
//					 dateStatisList.setOverloadAlarms(dataTo(mapl));
//				 }else if(i == 4) {
//					 dateStatisList.setOverVoltageAlarm(dataTo(mapl));
//				 }else if(i == 5) {
//					 dateStatisList.setUnderVoltageAlarm(dataTo(mapl));
//				 }else if(i == 6) {
//					 dateStatisList.setUseingTheAlarm(dataTo(mapl));
//				 }else if(i == 7) {
//					 dateStatisList.setUseingTheAlarm(dataTo(mapl));
//				 }else if(i == 8) {
//					 dateStatisList.setSmokeAlarm(dataTo(mapl));
//				 }
//    		}else {
//    			if(imeiList.size() > 0) {
//    				 electricAlarms = alarmMapper.timeAlarmCount(dataStatisticsVo,"0,1",imeiList);
//    				 List<DataTimeNum> listData = alarmMapper.selectByTimesAndType(dataStatisticsVo,i,imeiList);
//    				 Map<String,Integer> mapl = new HashMap<String, Integer>();
//    				 List<Map<String,Integer>> strList = new ArrayList<Map<String,Integer>>();
//    				 list.forEach(l ->{
//    					 mapl.put(TimeUtil.dateToStr(l), 0);
//    					 Map<String,Integer> map2 = new HashMap<String, Integer>();
//    					 map2.put(TimeUtil.dateToStr(l), 0);
//    					 strList.add(map2);
//    					 
//    				 });
//    				 listData.forEach(dataTimeNum ->{
//    					 	 mapl.put(dataTimeNum.getAddTime(), dataTimeNum.getNum());
//    					 });
//    				 if(i == 1) {
//    					dateStatisList.setOverCurrentAlarm(dataTo(mapl));
//    				 }else if(i == 2) {
//    					 dateStatisList.setTemperatureAlarm(dataTo(mapl));
//    				 }else if(i == 3) {
//    					 dateStatisList.setOverloadAlarms(dataTo(mapl));
//    				 }else if(i == 4) {
//    					 dateStatisList.setOverVoltageAlarm(dataTo(mapl));
//    				 }else if(i == 5) {
//    					 dateStatisList.setUnderVoltageAlarm(dataTo(mapl));
//    				 }else if(i == 6) {
//    					 dateStatisList.setUseingTheAlarm(dataTo(mapl));
//    				 }else if(i == 7) {
//    					 dateStatisList.setLeakageAlarm(dataTo(mapl));
//    				 }else if(i == 8) {
//    					 dateStatisList.setSmokeAlarm(dataTo(mapl));
//    				 }
//    			}
//    		}
//		}
		List<DataList> listDataList = null;
		if(imeiList.size() == 0) {
			imeiList = null;
		}
		if(auth.getUsername().equals(DataType.admin)) {
			eqptQueryVo.setAdminId(2);
			listDataList = alarmMapper.selectDataStatis(true,dataStatisticsVo,imeiList);
			electricAlarms = alarmMapper.timeAlarmCount(dataStatisticsVo,"0,1",null);
		}else {
			listDataList = alarmMapper.selectDataStatis(false,dataStatisticsVo,imeiList);
			if(imeiList != null) {
				electricAlarms = alarmMapper.timeAlarmCount(dataStatisticsVo,"0,1",imeiList);
			}
		}
		
		Map<String,String> mapl = new HashMap<String, String>();
		listDataList.forEach(xx ->{
			if(listTime.contains(xx.getAddTime())) {
				int index = listTime.lastIndexOf(xx.getAddTime());
				if(xx.getAlarmType() == 1) {
					int [] a = dateStatisList.getOverCurrentAlarm();
					a[index] = xx.getNum();
					dateStatisList.setOverCurrentAlarm(a);
				 }else if(xx.getAlarmType() == 2) {
					 int [] a = dateStatisList.getTemperatureAlarm();
					 a[index] = xx.getNum();
					 dateStatisList.setTemperatureAlarm(a);
				 }else if(xx.getAlarmType() == 3) {
					 int [] a = dateStatisList.getOverloadAlarms();
					 a[index] = xx.getNum();
					 dateStatisList.setOverloadAlarms(a);
				 }else if(xx.getAlarmType() == 4) {
					 int [] a = dateStatisList.getOverVoltageAlarm();
					 a[index] = xx.getNum();
					 dateStatisList.setOverVoltageAlarm(a);
				 }else if(xx.getAlarmType() == 5) {
					 int [] a = dateStatisList.getUnderVoltageAlarm();
					 a[index] = xx.getNum();
					 dateStatisList.setUnderVoltageAlarm(a);
				 }else if(xx.getAlarmType() == 6) {
					 int [] a = dateStatisList.getUseingTheAlarm();
					 a[index] = xx.getNum();
					 dateStatisList.setUseingTheAlarm(a);
				 }else if(xx.getAlarmType() == 7) {
					 int [] a = dateStatisList.getLeakageAlarm();
					 a[index] = xx.getNum();
					 dateStatisList.setLeakageAlarm(a);
				 }else if(xx.getAlarmType() == 8) {
					 int [] a = dateStatisList.getSmokeAlarm();
					 a[index] = xx.getNum();
					 dateStatisList.setSmokeAlarm(a);
				 }
			}
			String key = xx.getAddTime() +"_"+ xx.getAlarmType();
			mapl.put(key, xx.getNum()+"");
			System.out.println(xx.toString());
		});
    	int dxCount = equipmentMapper.queryInfoCount(eqptQueryVo,imeiList);
    	eqptQueryVo.setEqptType("1");
    	int sxCount = equipmentMapper.queryInfoCount(eqptQueryVo,imeiList);
    	
    	
    	dateStatisList.setElectricAlarmNum(electricAlarms);
    	dateStatisList.setElectricAlarmNums(dxCount + sxCount);
    	//烟感设备总数.
    	eqptQueryVo.setEqptType("2");
    	int ygCount = equipmentMapper.queryInfoCount(eqptQueryVo,imeiList);
    	//烟感告警设备数量
    	int ygAlarmCount = alarmMapper.timeAlarmCount(dataStatisticsVo,"2",imeiList);
    	dateStatisList.setSmokeNum(ygCount);
    	dateStatisList.setSmokeAlarmNum(ygAlarmCount);
    	return new ResultList(Code.QUERY_SUCCESS.getCode(),Code.QUERY_SUCCESS.getMsg(),new Result<DateStatisList>(1,dateStatisList));
	}
	public int [] dataTo(Map<String,Integer> map) {
		Map<String, Integer> resultMap = sortMapByKey(map); 
		String strArr = "";
		for(Map.Entry<String, Integer> entry : resultMap.entrySet()){
		    String mapKey = entry.getKey();
		    String mapValue = entry.getValue().toString();
		    strArr += mapValue + ",";
		}
		System.out.println(strArr);
		return Arrays.asList(strArr.split(",")).stream().mapToInt(Integer::parseInt).toArray();
	}
	
	
	/**

     * 使用 Map按key进行排序

     * @param map

     * @return

     */

    public static Map<String, Integer> sortMapByKey(Map<String, Integer> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        Map<String, Integer> sortMap = new TreeMap<String, Integer>( new MapKeyComparator());
        sortMap.putAll(map);
        return sortMap;
    }
	@Override
	public ResultList queryInfoByImei(DataStatisticsVo dataStatisticsVo) {
		DateStatis dateStatis = new DateStatis();
		for(int i = 1;i<9;i++) {
			 Map<String,Integer> mapl = new HashMap<String, Integer>();
			 Integer count = alarmMapper.selectByTimeAndTypeCount(dataStatisticsVo,i);
			 if(count == null) {
				 count = 0;
			 }
			 if(i == 1) {
				 dateStatis.setOverCurrentAlarm(count);
			 }else if(i == 2) {
				 dateStatis.setTemperatureAlarm(count);
			 }else if(i == 3) {
				 dateStatis.setOverloadAlarms(count);
			 }else if(i == 4) {
				 dateStatis.setOverVoltageAlarm(count);
			 }else if(i == 5) {
				 dateStatis.setUnderVoltageAlarm(count);
			 }else if(i == 6) {
				 dateStatis.setUseingTheAlarm(count);
			 }else if(i == 7) {
				 dateStatis.setLeakageAlarm(count);
			 }else if(i == 8) {
				 dateStatis.setElectricAlarmTotalFailure(count);
			 }
		}
		// TODO Auto-generated method stub
		return new ResultList(Code.QUERY_SUCCESS.getCode(),Code.QUERY_SUCCESS.getMsg(),new Result<>(1,dateStatis));
	}
	@Override
	public Response<DevStatis> devStatis() {
		AuthUser authUser = HttpServletUtils.getAuthUser();
		// TODO Auto-generated method stub
		List<IMEI> imeiList = equipmentMapper.selectUserImei(authUser.getRoleId(),authUser.getUid());
		AuthUser auth = authUserMapper.selectByPrimaryKey(authUser.getUid());
		EqptQueryVo eqptQueryVo = new EqptQueryVo();
		eqptQueryVo.setUserId(authUser.getUid());
		if(auth.getUsername().equals(DataType.admin)) {
			eqptQueryVo.setAdminId(2);
			eqptQueryVo.setUserId(null);
		}
		List<EqptInfoDto> list = new ArrayList<EqptInfoDto>();
		list = equipmentMapper.querInfoByUserIdPage(eqptQueryVo,imeiList);
		list.forEach(eqpt -> {
			eqpt.setEqptStatus(String.valueOf(Code.NOTONLINE.getCode()));
              if (redisUtil.hasKey(eqpt.getImei())) {
            	  eqpt.setEqptStatus(String.valueOf(Code.ONLINE.getCode()));
              }
		});
		Integer ONLINE = Code.ONLINE.getCode();
		Integer onLineCount = list.stream().filter(x -> x.getEqptStatus().equals(ONLINE.toString())).collect(Collectors.toList()).size();
		Integer onePhaseCount = list.stream().filter(x -> x.getEqptType().equals(DataType.ONE_PHASE.toString())).collect(Collectors.toList()).size();
		DevStatis dev = new DevStatis(onLineCount, list.size() - onLineCount, onePhaseCount, list.size() - onePhaseCount, list.size());
		return new Response<DevStatis>().Success(Code.OK,Code.OK.getMsg(),dev);
	}
	@Override
	public ResponseList<DevAlarmListDto> devAlarmList(Long startTime, Long endTime) {
		// TODO Auto-generated method stub
		AuthUser authUser = HttpServletUtils.getAuthUser();
		// TODO Auto-generated method stub
		List<IMEI> imeiList = equipmentMapper.selectUserImei(authUser.getRoleId(),authUser.getUid());
		AuthUser auth = authUserMapper.selectByPrimaryKey(authUser.getUid());
		boolean isAdmin = false; 
		if(auth.getUsername().equals(DataType.admin)) {
			isAdmin = true;
		}
		if(imeiList.size() == 0) {
			imeiList = null;
		}
		List<DevAlarmListDto> list = alarmMapper.selectDevAlarmList(TimeUtil.timeStamp2Date(startTime.toString()),TimeUtil.timeStamp2Date(endTime.toString()),isAdmin,imeiList);
		for(int i = 0;i<list.size();i++) {
			list.get(i).setIdx(i + 1);
		}
		return new ResponseList<DevAlarmListDto>().Success(Code.OK,Code.OK.getMsg(),list);
	}
}
