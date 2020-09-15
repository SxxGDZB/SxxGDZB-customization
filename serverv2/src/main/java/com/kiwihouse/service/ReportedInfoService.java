package com.kiwihouse.service;

import java.text.ParseException;
import java.util.Map;

import com.kiwihouse.vo.entire.ResultList;
import com.kiwihouse.vo.kiwihouse.AlmQueryVo;
import com.kiwihouse.vo.kiwihouse.QueryPwrVo;
import com.kiwihouse.vo.kiwihouse.ReportedQueryVo;

public interface ReportedInfoService {
	/**
	 * 	查询设备上报信息
	 * @param reportedQueryVo
	 * @return
	 */
	ResultList queryInfo(ReportedQueryVo reportedQueryVo);
	/**
	 * 	查询设备告警信息
	 * @param almQueryVo
	 * @return
	 */
	ResultList  queryAlmInfo(AlmQueryVo almQueryVo);
	/**
	 * 	查询设备上报功率数据
	 * @param queryPwrVo
	 * @return
	 */
	ResultList queryPwr(QueryPwrVo queryPwrVo) throws ParseException;
	/**
	 * 	设备运行信息
	 * @param reportedQueryVo
	 * @return
	 */
	ResultList devRunInfo(ReportedQueryVo reportedQueryVo);
	/**
	 * 	火警设备告警信息
	 * @param reportedQueryVo
	 * @return
	 */
	ResultList devAlarmInfo(ReportedQueryVo reportedQueryVo);

}
