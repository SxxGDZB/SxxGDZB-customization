package com.kiwihouse.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.kiwihouse.dao.entity.Buttons;
import com.kiwihouse.dao.entity.MenuBtnModel;
import com.kiwihouse.dao.entity.ResBtnModel;

public interface MenuBtnMapper {
	/**
	 * 	批量添加或修改资源-按钮表
	 * @param roleResourceButtons
	 */
	int insertOrUpdateBatch(List<MenuBtnModel> list);
	
	/**
	 * 根据静态资源ID查询按钮列表
	 * @param resourceId 静态资源ID
	 * @param roleId 角色ID
	 * @return
	 */
	List<Buttons> buttonListById(@Param("menuId") Integer menuId, @Param("roleId")  Integer roleId);
	/**
	 * 	根据角色删除
	 * @param roleId
	 */
	int delete(Integer roleId);
	/**
	 * 	资源按钮模板
	 * @param page
	 * @param limit
	 * @return
	 */
	List<ResBtnModel> queryInfo(Integer page, Integer limit,Integer roleId);
	/**
	 * 	资源按钮模板总记录条数
	 * @return
	 */
	Integer queryCount(Integer roleId);
	/**
	 *	 模板按钮添加
	 * @param list
	 * @return
	 */
	int addModelBtn(List<MenuBtnModel> list);
	/**
	 * 	模板按钮添加或修改
	 * @param list
	 * @return
	 */
	int addOrUpdModelBtn(List<MenuBtnModel> list);
	/**
	 * 	删除某一个菜单页面全部按钮
	 * @param menuId
	 * @return
	 */
	int deleteModelBtn(Integer menuId);
	/**
	 * 根据菜单ID删除按钮关联
	 * @param menuId
	 */
	void deleteByMenuId(Integer menuId);

}
