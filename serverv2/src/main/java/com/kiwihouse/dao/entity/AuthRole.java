package com.kiwihouse.dao.entity;

import java.util.Date;
import java.util.List;

/**
 *   角色
 * @author tomsun28
 * @date 9:42 2018/4/22
 */
public class AuthRole {
    private Integer id;

    private String code;

    private String name;

    private Short status;

    private Date createTime;

    private Date updateTime;
    
    private String groups;

    private Integer parentId;
    
    private String menuIds;
    
    private List<MenuRes> menuResList;
    
//    
//    private Integer roleId;
//    
//   
//
//	public Integer getRoleId() {
//		return roleId;
//	}
//
//	public void setRoleId(Integer roleId) {
//		this.roleId = roleId;
//	}

	public List<MenuRes> getMenuResList() {
		return menuResList;
	}

	public void setMenuResList(List<MenuRes> menuResList) {
		this.menuResList = menuResList;
	}

	public String getMenuIds() {
		return menuIds;
	}

	public void setMenuIds(String menuIds) {
		this.menuIds = menuIds;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getGroups() {
		return groups;
	}

	public void setGroups(String groups) {
		this.groups = groups;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
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

	@Override
	public String toString() {
		return "AuthRole [id=" + id + ", code=" + code + ", name=" + name + ", status=" + status + ", createTime="
				+ createTime + ", updateTime=" + updateTime + ", groups=" + groups + ", parentId=" + parentId
				+ ", menuIds=" + menuIds + ", menuResList=" + menuResList + "]";
	}
    
    
}