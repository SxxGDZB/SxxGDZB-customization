package com.kiwihouse.dao.mapper;

import java.util.List;

import com.kiwihouse.dao.entity.MenuRes;

public interface MenuResMapper {
	/**
	 *	根据RoleID获取静态页面以及对应的资源列表
	 * @param roleId
	 * @return
	 */
	List<MenuRes> resPageByRoleId(Integer roleId);
	/**
	 * 	根据角色ID删除、页面资源关联信息
	 * @param roleId
	 * @return
	 */
	int delMenuResByRoleId(Integer roleId);
	/**
	 * 	批量添加、页面资源关联信息
	 * @param list
	 * @return
	 */
	int insertBatch(List<MenuRes> list);

}
