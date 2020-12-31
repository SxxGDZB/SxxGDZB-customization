package com.kiwihouse.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 	设备告警列表bean
 * @author cmx
 *
 */
@ToString
@Getter
@Setter
public class DevAlarmListDto {
	@ApiModelProperty(name = "devName",value = "设备名称")
	private String devName;
	@ApiModelProperty(name = "alarmAount",value = "告警数量")
	private int alarmAcount;
	@ApiModelProperty(name = "idx",value = "排序：告警最多的排前面")
	private int idx;
	public DevAlarmListDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public DevAlarmListDto(String devName, int alarmAcount) {
		super();
		this.devName = devName;
		this.alarmAcount = alarmAcount;
	}
	
	
}
