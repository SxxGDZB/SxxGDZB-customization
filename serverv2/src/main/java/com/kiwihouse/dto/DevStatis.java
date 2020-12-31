package com.kiwihouse.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@ApiModel("设备参数")
public class DevStatis {
	@ApiModelProperty(name = "onLineCount",value = "在线数量")
	private int onLineCount;
	@ApiModelProperty(name = "offLineCount",value = "离线数量")
	private int offLineCount;
	@ApiModelProperty(name = "onePhase",value = "单相数量")
	private int onePhase;
	@ApiModelProperty(name = "threePhase",value = "三相数量")
	private int threePhase;
	@ApiModelProperty(name = "totalCount",value = "总数量")
	private int totalCount;
	public DevStatis() {
		super();
		// TODO Auto-generated constructor stub
	}
	public DevStatis(int onLineCount, int offLineCount, int onePhase, int threePhase, int totalCount) {
		super();
		this.onLineCount = onLineCount;
		this.offLineCount = offLineCount;
		this.onePhase = onePhase;
		this.threePhase = threePhase;
		this.totalCount = totalCount;
	}
	
}
