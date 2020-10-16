package com.kiwihouse.dao.entity;


/**
 * <p>
 * 
 * </p>
 *
 * @author sxx
 * @since 2020-10-16
 */
public class RoleResourceButton {


    /**
     * ID
     */
	private Integer id;
    /**
     * 按钮资源ID
     */
	private Integer resourceButtonId;
    /**
     * 静态资源ID
     */
	private Integer resourceId;
    /**
     * 角色ID
     */
	private Integer roleId;

	private Buttons resourceButtons;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getResourceButtonId() {
		return resourceButtonId;
	}

	public void setResourceButtonId(Integer resourceButtonId) {
		this.resourceButtonId = resourceButtonId;
	}

	public Integer getResourceId() {
		return resourceId;
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Buttons getResourceButtons() {
		return resourceButtons;
	}

	public void setResourceButtons(Buttons resourceButtons) {
		this.resourceButtons = resourceButtons;
	}

	@Override
	public String toString() {
		return "RoleResourceButton [id=" + id + ", resourceButtonId=" + resourceButtonId + ", resourceId=" + resourceId
				+ ", roleId=" + roleId + ", resourceButtons=" + resourceButtons.toString() + "]";
	}

	
}
