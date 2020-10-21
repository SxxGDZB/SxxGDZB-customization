package com.kiwihouse.service;

import java.util.List;
import java.util.Map;

import com.kiwihouse.dao.entity.MenuBtnModel;
import com.kiwihouse.domain.vo.Response;

public interface MenuBtnService {

	/**
	 * 根据静态资源ID查询按钮列表
	 * @param resourceId 静态资源
	 * @param roleId  角色ID
	 * @return
	 */
	Response buttonListById(Integer menuId, Integer roleId);

	/**
	 * 	添加或修改按钮权限
	 * @param roleResourceButtons
	 * @param roleId 
	 * @return
	 */
	Response insertOrUpdateBatch(List<MenuBtnModel> resourceButtons, Integer roleId);

	
	/**
	 * 	查询资源按钮模板
	 * @param page
	 * @param limit
	 * @return
	 */
	Map<String, Object> queryInfo(Integer page, Integer limit,Integer roleId);
	/**
	 * 	添加模板按钮
	 * @param list
	 * @return
	 */
	Response addModelBtn(List<MenuBtnModel> list);
	/**
	 * 	添加或修改
	 * @param list
	 * @return
	 */
	Response addOrUpdModelBtn(List<MenuBtnModel> list);
	/**
	 * 删除模板一个菜单所有按钮
	 * @param menuId
	 * @return
	 */
	Response deleteModelBtn(Integer menuId);
	
}
