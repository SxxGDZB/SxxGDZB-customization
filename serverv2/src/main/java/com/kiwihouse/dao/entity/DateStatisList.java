package com.kiwihouse.dao.entity;


import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class DateStatisList {
	/*用电告警器 单三相 ---> 总数*/
	@ApiModelProperty(name = "electricAlarmNums",value = "用电告警器 单三相 ---> 总数")
	private int electricAlarmNums;
	/*用电告警器 单三相 告警数量*/
	@ApiModelProperty(name = "electricAlarmNum",value = "用电告警器 单三相 告警数量")
	private int  electricAlarmNum;
	/*烟感告警器总数量*/
	@ApiModelProperty(name = "smokeNum",value = "烟感告警器总数量")
	private int smokeNum;
	/*烟感告警器告警数量*/
	@ApiModelProperty(name = "smokeAlarmNum",value = "烟感告警器告警数量")
	private int smokeAlarmNum;
	/*过压告警  ---> type == 4*/
	@ApiModelProperty(name = "overVoltageAlarm",value = "过压告警")
	private int [] overVoltageAlarm;
	/*过流告警  ---> type == 1*/
	@ApiModelProperty(name = "overCurrentAlarm",value = "过流告警")
	private int [] overCurrentAlarm;
	/*过载告警  ---> type == 3*/
	@ApiModelProperty(name = "overloadAlarms",value = "过载告警")
	private int [] overloadAlarms;
	/*欠压告警  ---> type == 5*/
	@ApiModelProperty(name = "underVoltageAlarm",value = "欠压告警 ")
	private int [] underVoltageAlarm;
	/*漏电告警  ---> type == 7*/
	@ApiModelProperty(name = "leakageAlarm",value = "漏电告警")
	private int [] leakageAlarm;
	/*温升告警 ---> type == 2*/
	@ApiModelProperty(name = "temperatureAlarm",value = "温升告警")
	private int [] temperatureAlarm;
	/*烟雾告警*/
	@ApiModelProperty(name = "smokeAlarm",value = "烟雾告警")
	private int [] smokeAlarm;
	/*用电告警总故障数*/
	@ApiModelProperty(name = "electricAlarmTotalFailure",value = "用电告警总故障数")
	private int [] electricAlarmTotalFailure;
	/*掉电告警 ---> type == 6*/
	@ApiModelProperty(name = "useingTheAlarm",value = "掉电告警")
	private int [] useingTheAlarm;
	/* 时间轴 */
	@ApiModelProperty(name = "addTime",value = "时间轴")
	private String [] addTime;
	public DateStatisList() {
		super();
		// TODO Auto-generated constructor stub
	}
	public DateStatisList(int count) {
		super();
		this.overVoltageAlarm = new int[count];
		this.overCurrentAlarm = new int[count];
		this.overloadAlarms = new int[count];
		this.underVoltageAlarm = new int[count];
		this.leakageAlarm = new int[count];
		this.temperatureAlarm = new int[count];
		this.smokeAlarm = new int[count];
		this.electricAlarmTotalFailure = new int[count];
		this.useingTheAlarm = new int[count];
	}
	
}
