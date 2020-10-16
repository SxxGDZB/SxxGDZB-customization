package com.kiwihouse.service;

import com.kiwihouse.domain.vo.Response;

public interface RoleResourceButtonService {
	/**
	 * 根据静态资源ID查询按钮列表
	 * @param resourceId 静态资源
	 * @param roleId  角色ID
	 * @return
	 */
	Response buttonListById(Integer resourceId, Integer roleId);
	
}
