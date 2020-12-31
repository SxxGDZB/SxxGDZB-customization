package com.kiwihouse.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.kiwihouse.dao.entity.MenuBtnModel;
import com.kiwihouse.dao.entity.SysMenu;
import com.kiwihouse.dto.ResMenu;


public interface SysMenuMapper {
    int deleteByPrimaryKey(Integer menuId);

    int insert(SysMenu record);

    int insertSelective(SysMenu record);

    SysMenu selectByPrimaryKey(Integer menuId);

    int updateByPrimaryKeySelective(SysMenu record);

    int updateByPrimaryKey(SysMenu record);
    /**
     * 	根据id获取用户所属权限菜单
     * @param visible 
     * @param uid
     * @return
     */
	List<SysMenu> getAuthMenuList(@Param(value = "roleId") Integer roleId,@Param(value = "visible")  Integer visible);
	/**
	 * 	修改菜单
	 * @param sysMenu
	 * @return
	 */
	int updateMenu(SysMenu sysMenu);
	/**
	 * 	根据ids修改删除菜单
	 * 
	 * @param ids
	 * @param visible  0,1
	 * @return
	 */
	int updateBatchMenuByIds(@Param(value = "ids") String[] ids,@Param(value = "visible") Integer visible);
	/**
	 * 批量删除菜单
	 * @param idsStrArr
	 * @return
	 */
	int deleteBatch(String[] idsStrArr);
	/**
	 * 	获取静态按钮页面列表
	 * @param roleId
	 * @return
	 */
	List<MenuBtnModel> getAuthMenuButtonLists(Integer roleId);
	/**
	 * 	根据路径获取菜单
	 * @param url
	 * @return
	 */
	SysMenu queryOneMenuByUrl(String url);
	/**
	 * 	获取菜单模块路径
	 * @return
	 */
	List<ResMenu> selectMenuUrlAndName();
	
}