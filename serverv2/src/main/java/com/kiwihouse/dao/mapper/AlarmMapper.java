package com.kiwihouse.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.kiwihouse.dao.entity.Alarm;
import com.kiwihouse.dao.entity.DataTimeNum;
import com.kiwihouse.dao.entity.IMEI;
import com.kiwihouse.dto.AlarmEqptDto;
import com.kiwihouse.vo.kiwihouse.AlmQueryVo;
import com.kiwihouse.vo.kiwihouse.DataStatisticsVo;
import com.kiwihouse.web.ReturnCode;

public interface AlarmMapper {
    int deleteByPrimaryKey(Integer alarmId);

    int insert(Alarm record);

    int insertSelective(Alarm record);

    Alarm selectByPrimaryKey(Integer alarmId);

    int updateByPrimaryKeySelective(Alarm record);

    int updateByPrimaryKey(Alarm record);
    /**
     * 批量添加
     * @param listAlarm
     */
	void insertBatch(List<Alarm> listAlarm);
	
	/**
	 * 查询告警记录
	 * @param almQueryVo
	 * @param list 
	 * @return
	 */
	List<AlarmEqptDto> queryAlarm(@Param("almQueryVo") AlmQueryVo almQueryVo,@Param("list") List<IMEI> list);
	/**
	 * 查询告警记录总数
	 * @param almQueryVo
	 * @return
	 */
	int queryAlarmCount(@Param("almQueryVo") AlmQueryVo almQueryVo,@Param("list") List<IMEI> list);
	
	Integer updateAlmSta(@Param("alarmId") String alarmId,@Param("almSta") int almSta);
	
	List<DataTimeNum> selectByTimesAndType(@Param("dataStatisticsVo") DataStatisticsVo dataStatisticsVo,@Param("alarmType") int alarmType, @Param("list") List<IMEI> list);
	/**
	 * 查询一个设备、一段时间的告警信息
	 * @param dataStatisticsVo
	 * @param i
	 * @return
	 */
	Integer selectByTimeAndTypeCount(@Param("dataStatisticsVo") DataStatisticsVo dataStatisticsVo,@Param("alarmType") int alarmType);
	/**
	 * 时间范围内 设备告警数量 
	 * @param list 
	 * @param startTime
	 * @param startTime2
	 * @param string
	 * @return
	 */
	int timeAlarmCount(@Param("dataStatisticsVo") DataStatisticsVo dataStatisticsVo, @Param("eqptType") String eqptType,@Param("list") List<IMEI> list);
	/**
	 * 清除设备告警
	 * @param imei
	 * @return
	 */
	int clearDevAlarms(String imei);

	List<ReturnCode> selectCode();
}