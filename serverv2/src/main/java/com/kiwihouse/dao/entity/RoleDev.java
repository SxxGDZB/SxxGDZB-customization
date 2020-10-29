package com.kiwihouse.dao.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 	角色设备
 * @author cmx
 *
 */
@ToString
@Getter
@Setter
public class RoleDev {

	private int id;
	private Integer roleId;
	private Integer eqptId;
	public RoleDev() {
		super();
		// TODO Auto-generated constructor stub
	}
	public RoleDev(int id, Integer roleId, Integer eqptId) {
		super();
		this.id = id;
		this.roleId = roleId;
		this.eqptId = eqptId;
	}
	public RoleDev(Integer roleId, Integer eqptId) {
		super();
		this.roleId = roleId;
		this.eqptId = eqptId;
	}
	
	
}
