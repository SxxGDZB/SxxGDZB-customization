package com.kiwihouse.service;

import java.util.List;
import java.util.Map;

import com.kiwihouse.dao.entity.MenuResModel;
import com.kiwihouse.domain.vo.Response;

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
	/**
	 * 	菜单资源模板不存在的资源列表查询
	 * @param page
	 * @param limit
	 * @param menuResModel
	 * @return
	 */
	Map<String, Object> selectMenuNotResList(Integer page, Integer limit, MenuResModel menuResModel);
	/**
	 * 	添加菜单模板资源
	 * @param list
	 * @return
	 */
	Response insertBatchMenuResModel(List<MenuResModel> list);
	/**
	 *	 删除菜单模板
	 * @param ids
	 * @return
	 */
	Response deleteBatchMenuResModel(String ids);
	/**
	 * 	根据RoleID获取静态页面以及对应的资源列表模板
	 * @param roleId
	 * @return
	 */
	Response resModelPageByRoleId(Integer roleId);

}
