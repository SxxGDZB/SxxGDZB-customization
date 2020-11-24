package com.kiwihouse.dao.entity;

import com.kiwihouse.util.excel.Excel;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@ToString
@Setter
@Getter
public class DevHistoryThree {

	 private Integer id;

	    private String dataJson;
	    
	    private String imei;
	    
	    @Excel(name = "读数时间")
	    private String addTime;

	    private String type;

	    private Integer eqptType;
	    private Float cur;
	    @Excel(name = "A相电流")
	    private Float curA;
	    @Excel(name = "B相电流")
	    private Float curB;
	    @Excel(name = "C相电流")
	    private Float curC;
	    private Float vol;
	    @Excel(name = "A相电压")
	    private Float volA;
	    @Excel(name = "B相电压")
	    private Float volB;
	    @Excel(name = "C相电压")
	    private Float volC;
	    private Float pwr;
	    @Excel(name = "A相功率")
	    private Float pwrA;
	    @Excel(name = "B相功率")
	    private Float pwrB;
	    @Excel(name = "C相功率")
	    private Float pwrC;
	    @Excel(name = "电能")
	    private Float kwh;
	    @Excel(name = "温升")
	    private Float lineTemp;
	    @Excel(name = "漏电流")
	    private Float leakCur;
	    @Excel(name = "设备信号强度")
	    private Integer csq;
	    private Float pwrFct;
	    @Excel(name = "A相功率因数")
	    private Float pwrFctA;
	    @Excel(name = "B相功率因数")
	    private Float pwrFctB;
	    @Excel(name = "C相功率因数")
	    private Float pwrFctC;
}
