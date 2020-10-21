package com.kiwihouse.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.kiwihouse.dao.entity.Buttons;
import com.kiwihouse.dao.entity.RoleResourceButton;

public interface RoleResourceButtonMapper {
	/**
	 * 根据静态资源ID查询按钮列表
	 * @param resourceId 静态资源ID
	 * @param roleId 角色ID
	 * @return
	 */
	List<Buttons> buttonListById(@Param("resourceId") Integer resourceId, @Param("roleId")  Integer roleId);
	/**
	 * 	添加或修改按钮权限
	 * @param roleResourceButtons
	 * @return
	 */
	int insertOrUpdateBatch(List<RoleResourceButton> list);

}
