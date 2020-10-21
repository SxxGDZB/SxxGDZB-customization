package com.kiwihouse.dao.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class Buttons {
	/*ID*/
	private Integer id;
	/*按钮提示*/
	private String title;
	/*按钮图标*/
	private String icon;
	/*按钮类型*/
	private Integer type;
	/*按钮操作*/
	private String event;
	/*静态资源路径*/
	private String url;
	/*静态资源名称*/
	private String name;
	/*静态资源 code码*/
	private String code;
	/*菜单ID*/
	private Integer menuId;
	/*角色ID*/
	private Integer roleId;
	
	
	
	
}
