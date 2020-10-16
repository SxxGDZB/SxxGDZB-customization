package com.kiwihouse.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.kiwihouse.dao.entity.Buttons;

public interface RoleResourceButtonMapper {
	/**
	 * 根据静态资源ID查询按钮列表
	 * @param resourceId 静态资源ID
	 * @param roleId 角色ID
	 * @return
	 */
	List<Buttons> buttonListById(@Param("resourceId") Integer resourceId, @Param("roleId")  Integer roleId);

}
