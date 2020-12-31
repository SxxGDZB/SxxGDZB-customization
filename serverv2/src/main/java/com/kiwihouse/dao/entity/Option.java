package com.kiwihouse.dao.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class Option {
	private String tabId;
	private String href;
	private String title;
	private String menuId;
	private boolean isIframe;
	private final int maxTabNum = 20;
	public Option() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Option(String tabId, String href, String title, String menuId, boolean isIframe) {
		super();
		this.tabId = tabId;
		this.href = href;
		this.title = title;
		this.menuId = menuId;
		this.isIframe = isIframe;
	}
	
	
}
