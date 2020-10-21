package com.kiwihouse.domain.vo;

public class AuthRoleResourceVo {
	private Integer id;
	private String resCode;
	private String roleCode;
	private String resName;
	private String roleName;
	private Integer roleId;
	//0  null查询已有的
	//1	   查询没有的
	//2     查询所有
	//3     查询当前角色的资源
	private Integer trigger;
	/**
	 * 资源ID 列表
	 */
	private String resIds;
	/**
	 * 1、静态页面资源
	 * 2、api资源
	 */
	private Integer type;
	
	
	
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getResIds() {
		return resIds;
	}
	public void setResIds(String resIds) {
		this.resIds = resIds;
	}
	public Integer getTrigger() {
		return trigger;
	}
	public void setTrigger(Integer trigger) {
		this.trigger = trigger;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getResCode() {
		return resCode;
	}
	public void setResCode(String resCode) {
		this.resCode = resCode;
	}
	public String getRoleCode() {
		return roleCode;
	}
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	public String getResName() {
		return resName;
	}
	public void setResName(String resName) {
		this.resName = resName;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	
}
