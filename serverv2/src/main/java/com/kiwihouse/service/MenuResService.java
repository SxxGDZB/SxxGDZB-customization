package com.kiwihouse.service;

import java.util.List;

import com.kiwihouse.dao.entity.MenuRes;
import com.kiwihouse.domain.vo.Response;

public interface MenuResService {
	/**
	 *	 根据RoleID获取静态页面以及对应的资源列表
	 * @param roleId
	 * @return
	 */
	Response resPageByRoleId(Integer roleId);
	/**
	 *	 批量修改菜单资源权限
	 * @param list
	 * @param valueOf
	 * @return
	 */
	Response menuResUpdBatch(List<MenuRes> list, Integer roleId);
	

}
