package com.kiwihouse.service;

import com.kiwihouse.domain.vo.Response;

public interface ResourceButtonService {
	/**
	 * 根据静态资源ID查询按钮列表
	 * @param resourceId
	 * @return
	 */
	Response buttonListById(Integer resourceId);
	
}
