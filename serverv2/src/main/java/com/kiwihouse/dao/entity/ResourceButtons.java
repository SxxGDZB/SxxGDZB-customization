package com.kiwihouse.dao.entity;

public class ResourceButtons {
	/*ID*/
	private Integer id;
	/*按钮提示*/
	private String title;
	/*按钮图标*/
	private String icon;
	/*按钮类型*/
	private Integer type;
	/*静态资源ID*/
	private Integer resourceId;
	/*按钮操作*/
	private String event;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getResourceId() {
		return resourceId;
	}
	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}
	public String getEvent() {
		return event;
	}
	public void setEvent(String event) {
		this.event = event;
	}
	
	
}
