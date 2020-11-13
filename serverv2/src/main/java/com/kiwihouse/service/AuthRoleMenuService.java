package com.kiwihouse.service;

import java.util.Map;

import com.kiwihouse.dao.entity.AuthRoleMenu;
import com.kiwihouse.domain.vo.AuthRoleMenuDetails;

public interface AuthRoleMenuService {
	/**
	 * 根据roleId查询角色对应菜单
	 * @param currentPage
	 * @param pageSize
	 * @param roleId
	 * @param curRoleId 
	 * @param authRoleMenuDetails 
	 * @param trigger 
	 * @return
	 */
	Map<String, Object> selectPage(Integer currentPage, 
			Integer pageSize, 
			Integer roleId, 
			Integer curRoleId,
			AuthRoleMenuDetails authRoleMenuDetails,
			Integer trigger);
	/**
	 * 批量添加角色菜单关联数据
	 * @param roleId
	 * @param ids
	 * @return
	 */
	boolean insertBatch(Integer roleId, String ids);
	/**
	 * 修改角色与菜单关联
	 * @param authRoleMenu
	 * @return
	 */
	boolean update(AuthRoleMenu authRoleMenu);
	/**
	 * 批量删除角色与菜单的绑定关系
	 * @param roleId
	 * @param menuIds
	 * @return
	 */
	boolean deleteRoleMenuByRoleMenuId(String roleId, String menuIds);
	/**
	 *  批量删除
	 * @param idsStrArr
	 * @return
	 */
	int deleteBatch(String[] idsStrArr);
	/**
	 * 
	 * @param ids
	 */
	void deleteBatchByMenuId(String[] ids);

}
