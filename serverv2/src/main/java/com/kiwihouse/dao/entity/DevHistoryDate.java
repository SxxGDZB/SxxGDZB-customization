package com.kiwihouse.dao.entity;

import com.kiwihouse.util.excel.Excel;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class DevHistoryDate {
	
    private Integer id;

    private String dataJson;
    
    private String imei;
    
    @Excel(name = "读数时间")
    private String addTime;

    private String type;

    private Integer eqptType;
    @Excel(name = "电流")
    private Float cur;
    private Float curA;
    private Float curB;
    private Float curC;
    @Excel(name = "电压")
    private Float vol;
    private Float volA;
    private Float volB;
    private Float volC;
    @Excel(name = "功率")
    private Float pwr;
    private Float pwrA;
    private Float pwrB;
    private Float pwrC;
    @Excel(name = "电能")
    private Float kwh;
    @Excel(name = "温升")
    private Float lineTemp;
    @Excel(name = "漏电流")
    private Float leakCur;
    @Excel(name = "设备信号强度")
    private Integer csq;
    @Excel(name = "功率因数")
    private Float pwrFct;
    private Float pwrFctA;
    private Float pwrFctB;
    private Float pwrFctC;
   
    
}