package com.kiwihouse.dao.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class IMEI {
	private String imei;

	public IMEI(String imei) {
		super();
		this.imei = imei;
	}

	public IMEI() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
