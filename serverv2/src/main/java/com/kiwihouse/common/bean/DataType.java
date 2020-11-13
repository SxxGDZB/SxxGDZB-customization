package com.kiwihouse.common.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 数据库字段值与含义对应关系
 * @author yjzn
 * @date 2020-1-13 20:22:04
 */
@ToString
@Setter
@Getter
public class DataType {

    public final static String SMOKE_REPORT_SC = "1";   //烟感设备自检信息
    public final static String SMOKE_REPORT_NORMAL = "2";   //烟感设备心跳信息
    public final static String SMOKE_REPORT_URGENT = "3";   //烟感设备紧急信息

    public final static String FIRE_REPORT_cl = "1";        //火警设备测量信息
    public final static String FIRE_REPORT_alm = "2";       //火警设备告警信息
    public final static String FIRE_REPORT_yx = "3";        //火警设备运行信息
    
    /*---------------->新增*/
    public final static String DEV_REAL_DATE = "1";//实时数据
    public final static String DEV_PARAMETER = "3";//设备参数
    
    /*---------------->设备类型*/
    /*单相*/
    public final static Integer ONE_PHASE = 0;
    /*三相*/
    public final static Integer THREE_PHASE = 1;
    
    public final static String admin = "admin";
    
    public final static Integer adminId = 1;
    
    /*分享设备时间  单位min*/
    public final static long shareTime = 30;
    
}
