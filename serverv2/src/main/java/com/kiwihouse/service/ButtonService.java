package com.kiwihouse.service;

import java.util.Map;

import com.kiwihouse.dao.entity.Buttons;
import com.kiwihouse.domain.vo.Response;

public interface ButtonService {
	/**
	 * 	查询系统按钮列表
	 * @param limit 
	 * @param page 
	 * @return
	 */
	Map<String, Object> queryInfo(Integer page, Integer limit);
	/**
	 * 	删除按钮
	 * @param idsArray
	 * @return
	 */
	Response deleteBatch(String[] idsArray);
	/**
	 * 	添加按钮
	 * @param resourceButtons
	 * @return
	 */
	Response insert(Buttons resourceButtons);
	/**
	 * 	修改按钮
	 * @param resourceButtons
	 * @return
	 */
	Response updateByPrimaryKey(Buttons resourceButtons);
	/**
	 *	 查询所有静态资源按钮权限
	 * @return
	 */
	Response queryButtonsPower();

}
