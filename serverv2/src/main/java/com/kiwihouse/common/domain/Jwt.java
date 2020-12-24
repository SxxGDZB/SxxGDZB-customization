package com.kiwihouse.common.domain;


import com.kiwihouse.dao.entity.AuthUser;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@ToString
public class Jwt {
	@ApiModelProperty(value = "jwt",name = "jwt")
	private String jwt;
	@ApiModelProperty(value = "用户类",name = "authUser")
	private AuthUser authUser;
	public Jwt() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Jwt(String jwt, AuthUser authUser) {
		super();
		this.jwt = jwt;
		this.authUser = authUser;
	}
	
	
}
