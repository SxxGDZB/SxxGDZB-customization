package com.kiwihouse.service;

import java.util.List;

import com.kiwihouse.dao.entity.SysMenu;
import com.kiwihouse.domain.vo.Response;

public interface SysMenuService {
	/**
	 * 根据id获取用户所属权限菜单
	 * @param visible 
	 * @param uid
	 * @return
	 */
	List<SysMenu> getAuthMenuList(Integer roleId, Integer visible);
	/**
	 * 	修改菜单
	 * @param sysMenu
	 * @return
	 */
	boolean updateMenu(SysMenu sysMenu);
	/**
	 * 批量修改菜单
	 * @param ids
	 * @param visible 
	 * @return
	 */
	boolean updateBatchMenuByIds(String[] ids, Integer visible);
	/**
	 * 添加菜单
	 * @param sysMenu 
	 * @return
	 */
	boolean insert(SysMenu sysMenu);
	/**
	 * 	批量删除菜单
	 * @param idsStrArr
	 * @return
	 */
	boolean deleteBatchMenuByIds(String[] idsStrArr);
	/**
	 * 	获取静态按钮页面列表
	 * @param roleId
	 * @return
	 */
	Response getAuthMenuButtonLists(Integer roleId);
	
	Response queryOneMenuByUrl(String url);
	

}
