package com.kiwihouse.service;

import com.kiwihouse.dto.DevAlarmListDto;
import com.kiwihouse.dto.DevStatis;
import com.kiwihouse.util.Response;
import com.kiwihouse.util.ResponseList;
import com.kiwihouse.vo.entire.ResultList;
import com.kiwihouse.vo.kiwihouse.DataStatisticsVo;

public interface DataStatisticsService {
	/**
	 * 数据总览所有人可查看
	 * @param dataStatisticsVo
	 * @return
	 */
	ResultList queryInfo(DataStatisticsVo dataStatisticsVo);
	/**
	 * 查询一个设备、一段时间的告警信息
	 * @param dataStatisticsVo
	 * @return
	 */
	ResultList queryInfoByImei(DataStatisticsVo dataStatisticsVo);
	/**
	 *	 查询设备在线离线数量
	 * @param roleId
	 * @param userId
	 * @return
	 */
	Response<DevStatis> devStatis();
	/**
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	ResponseList<DevAlarmListDto> devAlarmList(Long startTime, Long endTime);

}
