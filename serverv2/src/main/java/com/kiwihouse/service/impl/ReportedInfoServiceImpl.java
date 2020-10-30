package com.kiwihouse.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kiwihouse.common.bean.Code;
import com.kiwihouse.common.bean.DataType;
import com.kiwihouse.common.utils.GroupList;
import com.kiwihouse.common.utils.TimeUtil;
import com.kiwihouse.dao.entity.AuthUser;
import com.kiwihouse.dao.entity.DevInfo;
import com.kiwihouse.dao.entity.IMEI;
import com.kiwihouse.dao.mapper.AlarmMapper;
import com.kiwihouse.dao.mapper.AuthRoleMapper;
import com.kiwihouse.dao.mapper.AuthUserMapper;
import com.kiwihouse.dao.mapper.DevInfoMapper;
import com.kiwihouse.dao.mapper.EquipmentMapper;
import com.kiwihouse.dao.mapper.ReportedInfoMapper;
import com.kiwihouse.domain.vo.Response;
import com.kiwihouse.dto.AlarmEqptDto;
import com.kiwihouse.dto.FirePwrDto;
import com.kiwihouse.dto.ImprovedReportedDto;
import com.kiwihouse.dto.ImprovedSetParamDto;
import com.kiwihouse.dto.ImprovedWarnMsgDto;
import com.kiwihouse.dto.PwrMsg;
import com.kiwihouse.dto.ReportedDto;
import com.kiwihouse.dto.WarmMsgValue;
import com.kiwihouse.dto.WarnMsgDto;
import com.kiwihouse.dto.SinglePhase.SinglePhasePowerDto;
import com.kiwihouse.service.ReportedInfoService;
import com.kiwihouse.vo.entire.Result;
import com.kiwihouse.vo.entire.ResultList;
import com.kiwihouse.vo.kiwihouse.AlmQueryVo;
import com.kiwihouse.vo.kiwihouse.QueryPwrVo;
import com.kiwihouse.vo.kiwihouse.Register;
import com.kiwihouse.vo.kiwihouse.ReportedQueryVo;

@Service
public class ReportedInfoServiceImpl implements ReportedInfoService{
	@Autowired
    ReportedInfoMapper reportedInfoMapper;
	@Autowired
	AlarmMapper alarmMapper;
	@Autowired
	DevInfoMapper devInfoMapper;
	@Autowired
	EquipmentMapper equipmentMapper;
	@Autowired
	AuthRoleMapper authRoleMapper;
	@Autowired
	AuthUserMapper authUserMapper;
	static final Object room = new Object();
	static List<FirePwrDto>  lists = new ArrayList<FirePwrDto>();
    /**
     * 查询四类上报信息(测量数据,告警信息,运行数据,设置参数)
     *
     * @param reportedQueryVo 查询参数
     * @return 上报信息
     */
	@Override
    public ResultList queryInfo(ReportedQueryVo reportedQueryVo) {
//        if (!userInfo.isAdmin()) {
//            reportedQueryVo.setAdminId("");
//            reportedQueryVo.setUserId(userInfo.getUserId());
//        }
        List<ReportedDto> list = reportedInfoMapper.queryInfo(reportedQueryVo);
        if (list.isEmpty()) {
            return new ResultList(Code.QUERY_NULL.getCode(), Code.QUERY_NULL.getMsg(), null);
        } else {
            Integer row = reportedInfoMapper.queryInfoRow(reportedQueryVo);
            String alarmType = reportedQueryVo.getAlarmType();
            List returnList;
            if (DataType.FIRE_REPORT_cl.equals(alarmType)) {
                returnList = handleReportedDto(
                        list,
                        reportedQueryVo.getOrderBy(),
                        reportedQueryVo.getSequence(),
                        alarmType,
                        reportedQueryVo,
                        reportedQueryVo.getPage(),
                        reportedQueryVo.getLimit()
                );
            } else if (DataType.FIRE_REPORT_alm.equals(alarmType)) {
                returnList = handleReportedDto(
                        list,
                        reportedQueryVo.getOrderBy(),
                        reportedQueryVo.getSequence(),
                        alarmType,
                        reportedQueryVo,
                        reportedQueryVo.getPage(),
                        reportedQueryVo.getLimit()
                );
            } else if (DataType.FIRE_REPORT_yx.equals(alarmType)) {
                returnList = handleReportedDto(
                        list,
                        reportedQueryVo.getOrderBy(),
                        reportedQueryVo.getSequence(),
                        alarmType,
                        reportedQueryVo,
                        reportedQueryVo.getPage(),
                        reportedQueryVo.getLimit()
                );
            } else {
                return new ResultList(Code.PARAM_FORMAT_ERROR.getCode(), "alarmType值不正确", null);
            }
            if (null == returnList || returnList.isEmpty()) {
                return new ResultList(Code.QUERY_FAIL.getCode(), Code.QUERY_FAIL.getMsg(), null);
            } else {
                return new ResultList(Code.QUERY_SUCCESS.getCode(), Code.QUERY_SUCCESS.getMsg(), new Result<>(row, returnList));
            }
        }
    }

    /**
     * 处理设备上报数据结果集，根据排序参数排序,然后分页
     *
     * @param list
     * @param orderBy
     * @param page
     * @param limit
     */
    private List handleReportedDto(List<ReportedDto> list, String orderBy, String sequence, String alarmType, ReportedQueryVo reportedQueryVo, int page, int limit) {

        if (DataType.FIRE_REPORT_cl.equals(alarmType)) {
            List<ImprovedReportedDto> returnList = new ArrayList<>();
            List<ImprovedReportedDto> ImprovedReturnList;

            list.forEach(reportedDto -> {
                String alarmMsg = reportedDto.getAlarmMsg();
                ImprovedReportedDto improvedReportedDto = JSONObject.parseObject(alarmMsg, ImprovedReportedDto.class);
                improvedReportedDto.setAddTime(reportedDto.getAddTime())
                        .setAlarmType(reportedDto.getAlarmType())
                        .setEqptSn(reportedDto.getEqptSn())
                        .setImei(reportedDto.getImei());
                improvedReportedDto.setPwr(Math.abs(improvedReportedDto.getPwr())); //功率取绝对值
                improvedReportedDto.setPwr_fct(Math.abs(improvedReportedDto.getPwr_fct())); //功率因数取绝对值
                returnList.add(improvedReportedDto);
            });

            //TODO 排序待优化-代码复用
            if ("ascending".equals(sequence)) {
                if ("addTime".equals(orderBy)) {
                    ImprovedReturnList = returnList.stream()
                            .sorted(Comparator.comparing(ImprovedReportedDto::getAddTime))
                            .collect(Collectors.toList());
                } else if ("csq".equals(orderBy)) {
                    ImprovedReturnList = returnList.stream()
                            .sorted(Comparator.comparing(ImprovedReportedDto::getCsq))
                            .collect(Collectors.toList());
                } else if ("cur".equals(orderBy)) {
                    ImprovedReturnList = returnList.stream()
                            .sorted(Comparator.comparing(ImprovedReportedDto::getCur))
                            .collect(Collectors.toList());
                } else if ("vol".equals(orderBy)) {
                    ImprovedReturnList = returnList.stream()
                            .sorted(Comparator.comparing(ImprovedReportedDto::getVol))
                            .collect(Collectors.toList());
                } else if ("pwr".equals(orderBy)) {
                    ImprovedReturnList = returnList.stream()
                            .sorted(Comparator.comparing(ImprovedReportedDto::getPwr))
                            .collect(Collectors.toList());
                } else if ("kwh".equals(orderBy)) {
                    ImprovedReturnList = returnList.stream()
                            .sorted(Comparator.comparing(ImprovedReportedDto::getKwh))
                            .collect(Collectors.toList());
                } else if ("line_temp".equals(orderBy)) {
                    ImprovedReturnList = returnList.stream()
                            .sorted(Comparator.comparing(ImprovedReportedDto::getLine_temp))
                            .collect(Collectors.toList());
                } else if ("pwr_fct".equals(orderBy)) {
                    ImprovedReturnList = returnList.stream()
                            .sorted(Comparator.comparing(ImprovedReportedDto::getPwr_fct))
                            .collect(Collectors.toList());
                } else if ("leak_cur".equals(orderBy)) {
                    ImprovedReturnList = returnList.stream()
                            .sorted(Comparator.comparing(ImprovedReportedDto::getLeak_cur))
                            .collect(Collectors.toList());
                } else {
                    ImprovedReturnList = returnList;    //orderBy为空,则不进行排序
                }
            } else {
                if ("addTime".equals(orderBy)) {
                    ImprovedReturnList = returnList.stream()
                            .sorted(Comparator.comparing(ImprovedReportedDto::getAddTime).reversed())
                            .collect(Collectors.toList());
                } else if ("csq".equals(orderBy)) {
                    ImprovedReturnList = returnList.stream()
                            .sorted(Comparator.comparing(ImprovedReportedDto::getCsq).reversed())
                            .collect(Collectors.toList());
                } else if ("cur".equals(orderBy)) {
                    ImprovedReturnList = returnList.stream()
                            .sorted(Comparator.comparing(ImprovedReportedDto::getCur).reversed())
                            .collect(Collectors.toList());
                } else if ("vol".equals(orderBy)) {
                    ImprovedReturnList = returnList.stream()
                            .sorted(Comparator.comparing(ImprovedReportedDto::getVol).reversed())
                            .collect(Collectors.toList());
                } else if ("pwr".equals(orderBy)) {
                    ImprovedReturnList = returnList.stream()
                            .sorted(Comparator.comparing(ImprovedReportedDto::getPwr).reversed())
                            .collect(Collectors.toList());
                } else if ("kwh".equals(orderBy)) {
                    ImprovedReturnList = returnList.stream()
                            .sorted(Comparator.comparing(ImprovedReportedDto::getKwh).reversed())
                            .collect(Collectors.toList());
                } else if ("line_temp".equals(orderBy)) {
                    ImprovedReturnList = returnList.stream()
                            .sorted(Comparator.comparing(ImprovedReportedDto::getLine_temp).reversed())
                            .collect(Collectors.toList());
                } else if ("pwr_fct".equals(orderBy)) {
                    ImprovedReturnList = returnList.stream()
                            .sorted(Comparator.comparing(ImprovedReportedDto::getPwr_fct).reversed())
                            .collect(Collectors.toList());
                } else if ("leak_cur".equals(orderBy)) {
                    ImprovedReturnList = returnList.stream()
                            .sorted(Comparator.comparing(ImprovedReportedDto::getLeak_cur).reversed())
                            .collect(Collectors.toList());
                } else {
                    ImprovedReturnList = returnList;    //orderBy为空,则不进行排序
                }
            }

            //手动分页
            HashMap<Integer, List> map = GroupList.groupList(ImprovedReturnList, limit);
            return map.get(page - 1);
        } else if (DataType.FIRE_REPORT_alm.equals(alarmType)) {
            List<ImprovedWarnMsgDto> returnList = new ArrayList<>();

            list.forEach(reportedDto -> {
                String alarmMsg = reportedDto.getAlarmMsg();
                ImprovedWarnMsgDto improvedWarnMsgDto = JSONObject.parseObject(alarmMsg, ImprovedWarnMsgDto.class);
                improvedWarnMsgDto.setAddTime(reportedDto.getAddTime())
                        .setAlarmType(reportedDto.getAlarmType())
                        .setEqptSn(reportedDto.getEqptSn())
                        .setImei(reportedDto.getImei());
                returnList.add(improvedWarnMsgDto);
            });

            List<ImprovedWarnMsgDto> improvedWarnMsgDto = returnList.stream()
                    .sorted(Comparator.comparing(ImprovedWarnMsgDto::getAddTime).reversed())
                    .collect(Collectors.toList());

            HashMap<Integer, List> map = GroupList.groupList(improvedWarnMsgDto, limit);
            List<ImprovedWarnMsgDto> warnMsgDtoList = map.get(page - 1);
            warnMsgDtoList.forEach(improvedWarnMsgDto1 -> {
                String cur = improvedWarnMsgDto1.getCur();
                if (!"0".equals(cur)) {
                    String[] split = cur.split("-");
                    if (2 == split.length) {
                        improvedWarnMsgDto1.setCur(split[0]);
                        improvedWarnMsgDto1.setCurValue(split[1]);
                    }
                }
                String temp = improvedWarnMsgDto1.getTemp();
                if (!"0".equals(temp)) {
                    String[] split = temp.split("-");
                    if (2 == split.length) {
                        improvedWarnMsgDto1.setTemp(split[0]);
                        improvedWarnMsgDto1.setTempValue(split[1]);
                    }
                }
                String leak = improvedWarnMsgDto1.getLeak();
                if (!"0".equals(leak)) {
                    String[] split = leak.split("-");
                    if (2 == split.length) {
                        improvedWarnMsgDto1.setLeak(split[0]);
                        improvedWarnMsgDto1.setLeakValue(split[1]);
                    }
                }
                String overload = improvedWarnMsgDto1.getOverload();
                if (!"0".equals(overload)) {
                    String[] split = overload.split("-");
                    if (2 == split.length) {
                        improvedWarnMsgDto1.setOverload(split[0]);
                        improvedWarnMsgDto1.setOverloadValue(split[1]);
                    }
                }
                String vol = improvedWarnMsgDto1.getVol();
                if (!"0".equals(vol)) {
                    String[] split = vol.split("-");
                    if (2 == split.length) {
                        improvedWarnMsgDto1.setVol(split[0]);
                        improvedWarnMsgDto1.setVolValue(split[1]);
                    }
                }
            });
            return warnMsgDtoList;
        } else if (DataType.FIRE_REPORT_yx.equals(alarmType)) {
            List<ImprovedSetParamDto> returnList = new ArrayList<>();

            list.forEach(reportedDto -> {
                String alarmMsg = reportedDto.getAlarmMsg();
                ImprovedSetParamDto improvedSetParamDto = JSONObject.parseObject(alarmMsg, ImprovedSetParamDto.class);
                improvedSetParamDto.setAddTime(reportedDto.getAddTime())
                        .setAlarmType(reportedDto.getAlarmType())
                        .setEqptSn(reportedDto.getEqptSn())
                        .setImei(reportedDto.getImei());
                returnList.add(improvedSetParamDto);
            });

            List<ImprovedSetParamDto> improvedSetParamDto = returnList.stream()
                    .sorted(Comparator.comparing(ImprovedSetParamDto::getAddTime).reversed())
                    .collect(Collectors.toList());

            HashMap<Integer, List> map = GroupList.groupList(improvedSetParamDto, limit);
            return map.get(page - 1);
        }
        return null;
    }

    /**
     * @param almQueryVo 查询告警信息参数
     * @return 告警信息
     */
    @Override
	public Map<String, Object> queryAlmInfo(AlmQueryVo almQueryVo) {
    	 Map<String, Object> map = new HashMap<String, Object>();
        //获取角色的设备IMEI号
    	List<IMEI> imeiList = equipmentMapper.selectUserImei(Integer.valueOf(almQueryVo.getRoleId()));
        //查询用电设备工单
    	//AuthRole authRole =  authRoleMapper.selectIsAdmin(Integer.valueOf(almQueryVo.getUserId()));
    	AuthUser auth = authUserMapper.selectByPrimaryKey(Integer.valueOf(almQueryVo.getUserId()));
    	List<AlarmEqptDto> list = null;
    	if(auth.getUsername().equals(DataType.admin)) {
    		int count  = alarmMapper.queryAlarmCount(almQueryVo,null);
        	if(almQueryVo.getLimit() != null) {
        		almQueryVo.setPage((almQueryVo.getPage() - 1) * almQueryVo.getLimit());
        	}
        	list  = alarmMapper.queryAlarm(almQueryVo,null);
        	list.forEach(aed -> {
        		if("0".equals(aed.getEqptType())) {
        			aed.setAlarmMsg(onePhaseAlarm(aed.getAlarmMsg()));
        		}else if("1".equals(aed.getEqptType())) {
        			aed.setAlarmMsg(threePhaseAlarm(aed.getAlarmMsg()));
        		}
        	});
        	map.put("count", count);
        	map.put("data", list);
        	return map;
		}else {
			if(imeiList.size() > 0) {
	        	int count  = alarmMapper.queryAlarmCount(almQueryVo,imeiList);
	        	if(almQueryVo.getLimit() != null) {
	        		almQueryVo.setPage((almQueryVo.getPage() - 1) * almQueryVo.getLimit());
	        	}
	        	list  = alarmMapper.queryAlarm(almQueryVo,imeiList);
	        	list.forEach(aed -> {
	        		if("0".equals(aed.getEqptType())) {
	        			aed.setAlarmMsg(onePhaseAlarm(aed.getAlarmMsg()));
	        		}else if("1".equals(aed.getEqptType())) {
	        			aed.setAlarmMsg(threePhaseAlarm(aed.getAlarmMsg()));
	        		}
	        	});
	        	map.put("count", count);
	        	map.put("data", list);
	        	return map;
	        }
		}
        
        map.put("count", 0);
    	map.put("data", null);
		// TODO Auto-generated method stub
		return map;
	}
    /**
     * 查询功率数据
     * 设备没分钟读一次功率，然后累计一段时间(半个小时/一个小时)上报一次到后台
     * 故后台按开始时间和结束时间查询时如果上报时间不在这个区间，即使上报数据中的一部分数据在这个区间也是查不到的。
     * 所以每次查询都出来的都是这个时间段内，上报过数据的那一段累计时间(半个小时/一个小时)的数据
     *
     * @param queryPwrVo 查询参数
     * @return 每半个小时对应的数据源
     * @throws ParseException 
     */
    @Override
    public Response queryPwr(QueryPwrVo queryPwrVo) {
        if (!queryPwrVo.verifyType()) {
           return new Response().Success(Code.QUERY_FAIL, "参数值不正确");
        }

        //处理之前的开始时间和结束时间
        String startTime = queryPwrVo.getStartTime();
        String endTime = queryPwrVo.getEndTime();
        //扩大查询周期
        ExplainTime(queryPwrVo);
        List<FirePwrDto>  lists = reportedInfoMapper.queryPwr(queryPwrVo);
        if (lists.isEmpty()) {
            return new Response().Success(Code.QUERY_NULL, Code.QUERY_NULL.getMsg());
        }
        String dataBeginTime = lists.get(0).getAddTime();
        String dataEndTime = lists.get(lists.size() - 1).getAddTime();
        List<String> dateList = null;
        switch (queryPwrVo.getType()) {
            case "day":
                dateList = TimeUtil.getDayList(startTime, endTime, dataBeginTime, dataEndTime);
                break;
            case "min":
                dateList = TimeUtil.getMinLists(startTime, endTime, dataBeginTime, dataEndTime);
                break;
            case "hour":
                dateList = TimeUtil.getHourLists(startTime, endTime, dataBeginTime, dataEndTime);
                break;
        }

        List<SinglePhasePowerDto> tmpList = new ArrayList<>();
        List<SinglePhasePowerDto> returnList = new ArrayList<>();

        lists.forEach(x -> {
            PwrMsg pwrMsg = JSONObject.parseObject(x.getPwrMsg(), PwrMsg.class);
            List<Double> pwrList = pwrMsg.getPwr();
            List<String> minList = TimeUtil.getMinList(x.getAddTime(), pwrMsg.getNum(), true);
            for (int i = 0; i < pwrMsg.getNum(); i++) {
                tmpList.add(new SinglePhasePowerDto()
                        .setAddTime(minList.get(i))
                        .setPwr(pwrList.get(i)));
            }
        });
        switch (queryPwrVo.getType()) {
            case "hour":
            	System.out.println("进入------------>HOUR");
            case "day":
            	System.out.println("进入------------>DAY");
                dateList.forEach(x -> {
                    double maxPower = 0;
                    SinglePhasePowerDto item = null;
                    List<SinglePhasePowerDto> tmp = tmpList.stream().filter(y -> y.getAddTime().startsWith(x)).collect(Collectors.toList());
                    for (SinglePhasePowerDto dto : tmp) {
                        if (maxPower < dto.getPwr()) {
                            maxPower = dto.getPwr();
                            item = dto;
                        }
                    }
                    if (item != null) {
                        returnList.add(item);
                    }
                });
                return new Response().Success(Code.QUERY_SUCCESS, Code.QUERY_SUCCESS.getMsg()).addData("data", returnList);
            case "min":
            	System.out.println("进入------------>MIN");
            	return new Response().Success(Code.QUERY_SUCCESS, Code.QUERY_SUCCESS.getMsg()).addData("data", tmpList);
        }
        return new Response().Success(Code.QUERY_NULL, Code.QUERY_NULL.getMsg());
    }

    /**
     * 统计每小时或每分钟的功率峰值数据。
     *
     * @param list 按分钟查询所得结果
     * @param time 时间段,格式：2020-4-7 09:05 || 2020-4-7 09
     * @return 每分钟或每小时的功率峰值。
     */
    private Double handlePwr(List<HashMap<String, Double>> list, String time) {

        List<Double> timeList = new ArrayList<>();
        list.forEach(map -> {
            map.forEach((key, value) -> {
                if (key.startsWith(time)) {
                    timeList.add(value);
                }
            });
        });

        OptionalDouble max = timeList.stream().mapToDouble(Double::doubleValue).max();
        return max.isPresent() ? max.getAsDouble() : 0.0;
    }

    /**
     * 扩大查询时间周期，保证查询出的数据包括查询所有范围
     *
     * @param queryPwrVo 功率追踪查询参数
     */
    private void ExplainTime(QueryPwrVo queryPwrVo) {

    	
        queryPwrVo.setStartTime(TimeUtil.getPassSecTime(queryPwrVo.getStartTime(), -7200));
        queryPwrVo.setEndTime(TimeUtil.getPassSecTime(queryPwrVo.getEndTime(), 7200));
    }

    /**
     * 统计每分钟的上报的功率数据
     *
     * @param list 上报数据集合，集合中元素为一次累计上报的功率数组
     * @return
     */
    private HashMap<String, Double> handlePwrPreMin(List<FirePwrDto> list) {
        HashMap<String, Double> map = new HashMap<>();
        list.forEach(firePwrDto -> {
            //解析json数据
            PwrMsg pwrMsg = JSONObject.parseObject(firePwrDto.getPwrMsg(), PwrMsg.class);
            List<Double> pwrList = pwrMsg.getPwr();
            Collections.reverse(pwrList);   //原来list中数据是按照时间顺序排列，因为下面minList是按照时间倒序排列，所以将list中数据倒序存放
            firePwrDto.setPwr(pwrList);

            //将功率list中的值与时间对应保存在一起
            int num = pwrMsg.getNum();
            List<String> minList = TimeUtil.getMinList(firePwrDto.getAddTime(), num, false);
            for (int i = 0; i < num; i++) {
                map.put(minList.get(i), pwrList.get(i));
            }

        });
        return map;
    }

	@Override
	public Response devRunInfo(ReportedQueryVo reportedQueryVo,Integer type) {
		// TODO Auto-generated method stub
		try {
			DevInfo devInfo = devInfoMapper.selectDevByNewTime(reportedQueryVo.getImei(),type);
			//ReportedDto rep = reportedInfoMapper.devRunInfo(reportedQueryVo);
			if(devInfo == null) {
				return new Response().Success(Code.QUERY_NULL, Code.QUERY_NULL.getMsg());
			}
			ImprovedSetParamDto improvedSetParamDto = JSONObject.parseObject(devInfo.getDataJson(), ImprovedSetParamDto.class);
            improvedSetParamDto.setAddTime(devInfo.getAddTime())
                    .setAlarmType(devInfo.getType())
                    .setImei(devInfo.getImei());
            Register res = new Register();
            res.setReg_01(improvedSetParamDto.getCT().toString());
            res.setReg_02(improvedSetParamDto.getLCT().toString());
            res.setReg_03(improvedSetParamDto.getVolH().toString());
            res.setReg_04(improvedSetParamDto.getVolL().toString());
            res.setReg_05(improvedSetParamDto.getCurH().toString());
            res.setReg_06(improvedSetParamDto.getPwrH().toString());
            res.setReg_07(improvedSetParamDto.getLeakH().toString());
            res.setReg_08(improvedSetParamDto.getTempH().toString());
            res.setReg_09(improvedSetParamDto.getInterval().toString());
            res.setReg_10(improvedSetParamDto.getBeep().toString());
            res.setAddTime(devInfo.getAddTime());
            return new Response().Success(Code.QUERY_SUCCESS, Code.QUERY_SUCCESS.getMsg()).addData("data", res);
		} catch (Exception e) {
			// TODO: handle exception
			return new Response().Success(Code.QUERY_FAIL, Code.QUERY_FAIL.getMsg());
		}
	}

	@Override
	public Response devRealDate(ReportedQueryVo reportedQueryVo,Integer type) {
		// TODO Auto-generated method stub
//		try {
//			ReportedDto rep = reportedInfoMapper.devRunInfo(reportedQueryVo);
			DevInfo devInfo = devInfoMapper.selectDevByNewTime(reportedQueryVo.getImei(),type);
			if(devInfo == null) {
				return new Response().Success(Code.QUERY_NULL, Code.QUERY_NULL.getMsg());
			}
			ImprovedWarnMsgDto improvedWarnMsgDto = JSONObject.parseObject(devInfo.getDataJson(), ImprovedWarnMsgDto.class);
            improvedWarnMsgDto.setAddTime(devInfo.getAddTime())
                    .setAlarmType(devInfo.getType())
                    .setImei(devInfo.getImei());
			String cur = improvedWarnMsgDto.getCur();
            if (!"0".equals(cur) && StringUtils.isNotBlank(cur)) {
                String[] split = cur.split("-");
                if (2 == split.length) {
                	improvedWarnMsgDto.setCur(split[0]);
                	improvedWarnMsgDto.setCurValue(split[1]);
                }
            }
            String temp = improvedWarnMsgDto.getTemp();
            if (!"0".equals(temp) && StringUtils.isNotBlank(temp)) {
                String[] split = temp.split("-");
                if (2 == split.length) {
                	improvedWarnMsgDto.setTemp(split[0]);
                	improvedWarnMsgDto.setTempValue(split[1]);
                }
            }
            String leak = improvedWarnMsgDto.getLeak();
            if (!"0".equals(leak) && StringUtils.isNotBlank(leak)) {
                String[] split = leak.split("-");
                if (2 == split.length) {
                	improvedWarnMsgDto.setLeak(split[0]);
                	improvedWarnMsgDto.setLeakValue(split[1]);
                }
            }
            String overload = improvedWarnMsgDto.getOverload();
            if (!"0".equals(overload) && StringUtils.isNotBlank(overload)) {
                String[] split = overload.split("-");
                if (2 == split.length) {
                	improvedWarnMsgDto.setOverload(split[0]);
                	improvedWarnMsgDto.setOverloadValue(split[1]);
                }
            }
            String vol = improvedWarnMsgDto.getVol();
            if (!"0".equals(vol) && StringUtils.isNotBlank(vol)) {
                String[] split = vol.split("-");
                if (2 == split.length) {
                	improvedWarnMsgDto.setVol(split[0]);
                	improvedWarnMsgDto.setVolValue(split[1]);
                }
            }
            return new Response().Success(Code.QUERY_SUCCESS, Code.QUERY_SUCCESS.getMsg()).addData("data", improvedWarnMsgDto);
//		} catch (Exception e) {
//			// TODO: handle exception
//			return new Response().Success(Code.QUERY_FAIL, Code.QUERY_FAIL.getMsg());
//		}
	}
	
	public static String onePhaseAlarm(String str) {
		WarnMsgDto warnMsgDto = JSONObject.parseObject(str, WarnMsgDto.class);
		String cur = warnMsgDto.getCur();
		JSONArray ja = new JSONArray();
          if (!"0".equals(cur) && cur != null) {
              String[] split = cur.split("-");
              if (2 == split.length) {
                  WarmMsgValue warmMsgValue = new WarmMsgValue();
                  warmMsgValue.setMsg("过流告警");
                  warmMsgValue.setValue(split[1] + "A");
                  ja.add(warmMsgValue);
              }
          }
          String temp = warnMsgDto.getTemp();
       if (!"0".equals(temp) && StringUtils.isNotBlank(temp)) {
           String[] split = temp.split("-");
           if (2 == split.length) {
               WarmMsgValue warmMsgValue = new WarmMsgValue();
               warmMsgValue.setMsg("线温告警");
               warmMsgValue.setValue(split[1] + "℃");
               ja.add(warmMsgValue);
           }
       }
       String leak = warnMsgDto.getLeak();
       if (!"0".equals(leak) && StringUtils.isNotBlank(leak)) {
           String[] split = leak.split("-");
           if (2 == split.length) {
               WarmMsgValue warmMsgValue = new WarmMsgValue();
               warmMsgValue.setMsg("漏电流告警");
               warmMsgValue.setValue(split[1] + "mA");
               ja.add(warmMsgValue);
           }
       }
       String overload = warnMsgDto.getOverload();
       if (!"0".equals(overload) && StringUtils.isNotBlank(overload)) {
           String[] split = overload.split("-");
           if (2 == split.length) {
               WarmMsgValue warmMsgValue = new WarmMsgValue();
               warmMsgValue.setMsg("过载告警");
               warmMsgValue.setValue(split[1] + "W");
               ja.add(warmMsgValue);
           }
       }
       String vol = warnMsgDto.getVol();
       if (StringUtils.isNotBlank(vol)) {
         if (vol.startsWith("1")) {
             String[] split = vol.split("-");
             if (2 == split.length) {
                 WarmMsgValue warmMsgValue = new WarmMsgValue();
                 warmMsgValue.setMsg("过压告警");
                 warmMsgValue.setValue(split[1] + "V");
                 ja.add(warmMsgValue);
             }
         } else if (vol.startsWith("2")) {
             String[] split = vol.split("-");
             if (2 == split.length) {
                 WarmMsgValue warmMsgValue = new WarmMsgValue();
                 warmMsgValue.setMsg("欠压告警");
                 warmMsgValue.setValue(split[1] + "V");
                 ja.add(warmMsgValue);

             }
         } else if (vol.startsWith("3")) {
             String[] split = vol.split("-");
             if (2 == split.length) {
                 WarmMsgValue warmMsgValue = new WarmMsgValue();
                 warmMsgValue.setMsg("掉电告警");
                 warmMsgValue.setValue(split[1] + "V");
                 ja.add(warmMsgValue);
             }
         }
     }
		return ja.toJSONString();
	}
	
	
	public static String threePhaseAlarm(String str) {
		JSONArray ja = new JSONArray();
		JSONObject jo = (JSONObject) JSONObject.parse(str);
		if(jo != null) {
			JSONObject vol = (JSONObject) JSONObject.parse(jo.getString("vol"));
			JSONArray line = null;
			JSONArray value = null;
			JSONArray status = null;
			if(vol != null) {
				line = JSONArray.parseArray(vol.getString("line"));
				value = JSONArray.parseArray(vol.getString("value"));
				status = JSONArray.parseArray(vol.getString("status"));
			}
			
			if(line!= null) {
				//0-正常，1-过压，2-欠压，3-掉电
				for(int i = 0;i<line.size();i++) {
					System.out.println(line.get(i).toString());
					WarmMsgValue warmMsgValue = new WarmMsgValue();
					if("1".equals(status.get(i).toString())) {
						warmMsgValue.setMsg(line.get(i).toString() + "相 过压告警");
						warmMsgValue.setValue(value.get(i).toString());
					}else if("2".equals(status.get(i).toString())) {
						warmMsgValue.setMsg(line.get(i).toString() + "相 欠压告警");
						warmMsgValue.setValue(value.get(i).toString());
					}else if("3".equals(status.get(i).toString())) {
						warmMsgValue.setMsg(line.get(i).toString() + "相 掉电告警");
						warmMsgValue.setValue(value.get(i).toString());
					}
					System.out.println(warmMsgValue);
					ja.add(warmMsgValue);
				}
			}
			JSONObject cur = (JSONObject) JSONObject.parse(jo.getString("cur"));
			if(cur != null) {
				JSONArray cur_line = JSONArray.parseArray(cur.getString("line"));
				JSONArray cur_value = JSONArray.parseArray(cur.getString("value"));
				JSONArray cur_status = JSONArray.parseArray(cur.getString("status"));
				for(int i = 0;i<cur_line.size();i++) {
					System.out.println(cur_line.get(i).toString());
					WarmMsgValue warmMsgValue = new WarmMsgValue();
					if("1".equals(cur_status.get(i).toString())) {
						warmMsgValue.setMsg(cur_line.get(i).toString() + "相 电流过流告警");
						warmMsgValue.setValue(cur_value.get(i).toString());
					}
					System.out.println(warmMsgValue);
					ja.add(warmMsgValue);
				}
			}
			JSONObject leak = (JSONObject) JSONObject.parse(jo.getString("leak"));
			if(leak != null) {
				//JSONArray leak_line = JSONArray.parseArray(leak.getString("line"));
				WarmMsgValue warmMsgValue = new WarmMsgValue();
				warmMsgValue.setMsg( "漏电流告警");
				warmMsgValue.setValue(leak.getString("value"));
				ja.add(warmMsgValue);
			}
			
			JSONObject overload = (JSONObject) JSONObject.parse(jo.getString("overload"));
			if(overload != null) {
				JSONArray overload_line = JSONArray.parseArray(overload.getString("line"));
				JSONArray overload_value = JSONArray.parseArray(overload.getString("value"));
				JSONArray overload_status = JSONArray.parseArray(overload.getString("status"));
				for(int i = 0;i<overload_line.size();i++) {
					System.out.println(overload_line.get(i).toString());
					WarmMsgValue warmMsgValue = new WarmMsgValue();
					if("1".equals(overload_status.get(i).toString())) {
						warmMsgValue.setMsg(overload_line.get(i).toString() + "相 过载告警");
						warmMsgValue.setValue(overload_value.get(i).toString());
					}
					System.out.println(warmMsgValue);
					ja.add(warmMsgValue);
				}
			}
			JSONObject lineTemp = (JSONObject) JSONObject.parse(jo.getString("line_temp"));
		    if(lineTemp != null) {
		    	WarmMsgValue warmMsgValue = new WarmMsgValue();
		    	warmMsgValue.setMsg("线温告警");
		    	warmMsgValue.setValue(lineTemp.getString("value"));
		    	ja.add(warmMsgValue);
		    }
		}
		return ja.toJSONString();
	}
}
