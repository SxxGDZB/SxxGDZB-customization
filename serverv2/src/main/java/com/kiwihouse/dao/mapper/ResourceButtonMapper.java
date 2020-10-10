package com.kiwihouse.dao.mapper;

import java.util.List;

import com.kiwihouse.dao.entity.ResourceButtons;

public interface ResourceButtonMapper {
	/**
	 * 根据静态资源ID查询按钮列表
	 * @param resourceId
	 * @return
	 */
	List<ResourceButtons> buttonListById(Integer resourceId);

}
