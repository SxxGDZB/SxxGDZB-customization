package com.kiwihouse.dao.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class MenuBtnModel {
	private Integer menuId;
	
	private Integer buttonId;
	
	private String name;

	/*按钮提示*/
	private String title;
	/*按钮图标*/
	private String icon;
	/*按钮类型*/
	private Integer type;
	/*按钮操作*/
	private String event;
	
	private Integer roleId;
	/**/
	private Integer id;
	
}
