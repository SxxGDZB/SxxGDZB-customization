package com.kiwihouse.service;

import java.util.Map;

import com.kiwihouse.dao.entity.MenuResModel;

public interface MenuResModelService {
	/**
	 * 	遍历菜单资源模板
	 * @param page
	 * @param limit
	 * @param roleId
	 * @param menuResModel
	 * @return
	 */
	Map<String, Object> selectAllPage(Integer page, Integer limit,  MenuResModel menuResModel);

}
