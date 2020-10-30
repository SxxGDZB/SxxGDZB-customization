package com.kiwihouse.dao.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.kiwihouse.domain.vo.BaseTreeNode;

/**
 * <p>
 * 
 * </p>
 *
 * @author sxx
 * @since 2020-08-14
 */
public class SysMenu  extends BaseTreeNode implements Serializable{

	private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
	private Integer id;
    /**
     * 名称
     */
	private String name;
    /**
     * 父ID
     */
	private Integer parentId;
    /**
     * 显示顺序
     */
	private Integer orderNum;
    /**
     * 请求地址
     */
	private String url;
    /**
     * 是否隐藏 0显示 1隐藏
     */
	private String visible;
    /**
     * 权限标识
     */
	private String perms;
    /**
     * 字体图标
     */
	private String icon;
    /**
     * 创建者
     */
	private String createBy;
    /**
     * 创建时间
     */
	private Date createTime;
    /**
     * 更新者
     */
	private String updateBy;
    /**
     * 更新时间
     */
	private Date updateTime;
    /**
     * 备注
     */
	private String remark;

	private String href;
	
	private String title;
	
	private String target;
	
	private Integer isBasic;
	
	private Integer pageType;
	
	private List<Integer> ids = new ArrayList<Integer>();
	
	private Integer roleId;
	
	public Integer getPageType() {
		return pageType;
	}

	public void setPageType(Integer pageType) {
		this.pageType = pageType;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public List<Integer> getIds() {
		return ids;
	}

	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}

	public Integer getIsBasic() {
		return isBasic;
	}

	public void setIsBasic(Integer isBasic) {
		this.isBasic = isBasic;
	}

	public String getHref() {
		return this.url;
	}

	public void setHref(String href) {
		this.href = this.url;
	}

	public String getTitle() {
		return this.name;
	}

	public void setTitle(String title) {
		this.title = this.name;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getVisible() {
		return visible;
	}

	public void setVisible(String visible) {
		this.visible = visible;
	}

	public String getPerms() {
		return perms;
	}

	public void setPerms(String perms) {
		this.perms = perms;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}


}
