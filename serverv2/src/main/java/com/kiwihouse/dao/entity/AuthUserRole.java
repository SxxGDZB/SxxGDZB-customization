package com.kiwihouse.dao.entity;

import java.util.Date;

/**
 *   role-user
 * @author tomsun28
 * @date 9:50 2018/4/22
 */
public class AuthUserRole {
    private Integer id;

    private Integer userId;

    private Integer roleId;

    private Date createTime;

    private Date updateTime;

    private String roleName;
    
    public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

	public AuthUserRole() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AuthUserRole(Integer userId, Integer roleId) {
		super();
		this.userId = userId;
		this.roleId = roleId;
	}
    
    
}