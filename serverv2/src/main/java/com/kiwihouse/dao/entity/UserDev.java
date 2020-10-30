package com.kiwihouse.dao.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 用户设备列表 关联表
 * @author cmx
 *
 */
@ToString
@Setter
@Getter
public class UserDev {
	private int id;
	private Integer userId;
	private Integer eqptId;
	public UserDev() {
		super();
		// TODO Auto-generated constructor stub
	}
	public UserDev(Integer userId, Integer eqptId) {
		super();
		this.userId = userId;
		this.eqptId = eqptId;
	}
	
	
}
