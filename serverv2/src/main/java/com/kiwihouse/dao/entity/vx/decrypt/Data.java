package com.kiwihouse.dao.entity.vx.decrypt;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class Data {
	private String encryptedData;
	private String iv;
	private String code;
}
