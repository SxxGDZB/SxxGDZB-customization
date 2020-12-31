package com.kiwihouse.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class ResMenu {
	@ApiModelProperty(name = "name",value = "菜单名称")
	private String name;
	@ApiModelProperty(name = "url",value = "路径")
	private String url;
}
