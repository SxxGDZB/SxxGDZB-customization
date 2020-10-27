package com.kiwihouse.dao.entity.vx.dev;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>
 * 设备表
 * </p>
 *
 * @author sxx
 * @since 2020-10-26
 */
@ToString
@Setter
@Getter
public class WxEquipment {


    /**
     * 设备ID
     */
	private Integer eqptId;
    /**
     * onenet平台生成设备唯一ID
     */
	private Integer deviceId;
    /**
     * 设备名称
     */
	private String eqptName;
    /**
     * imei号
     */
	private String imei;
    /**
     * imsi号
     */
	private String imsi;
    /**
     * 设备序列号
     */
	private String eqptSn;
    /**
     * 设备类型0、单相 1、三相
     */
	private String eqptType;
    /**
     * 限定功率
     */
	private Float power;
    /**
     * 额定电压
     */
	private Integer voltage;
    /**
     * 额定电流
     */
	private Integer electricity;
    /**
     * 是否注册到onenet平台
     */
	private Integer register;
    /**
     * 设备详细地址
     */
	private String eqptAddr;
    /**
     * 纬度
     */
	private String latitude;
    /**
     * 经度
     */
	private String longitude;
    /**
     * 用户ID
     */
	private Integer userId;
    /**
     * 小区ID
     */
	private Integer siteId;
    /**
     * 所属角色ID
     */
	private Integer roleId;
    /**
     * 设备分组ID
     */
	private Integer groupId;
    /**
     * 设备上次上报时间（暂未使用）
     */
	private Date lastReportTime;
    /**
     * 数据录入时间
     */
	private Date addTime;
    /**
     * ICCID
     */
	private String iccid;
    /**
     * 图片
     */
	private String imgs;
    /**
     * 省
     */
	private String province;
    /**
     * 市
     */
	private String city;
    /**
     * 区
     */
	private String district;
	/**
	 * 在线状态
	 */
	private Integer online;
	
	private String eqptStatus;

}
