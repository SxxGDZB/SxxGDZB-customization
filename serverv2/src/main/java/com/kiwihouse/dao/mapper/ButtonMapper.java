package com.kiwihouse.dao.mapper;

import java.util.List;

import com.kiwihouse.dao.entity.Buttons;

public interface ButtonMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Buttons record);

    int insertSelective(Buttons record);

    Buttons selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Buttons record);

    int updateByPrimaryKey(Buttons record);
    /**
     * 	查询按钮列表
     * @param page
     * @param limit
     * @return
     */
	List<Buttons> queryInfo(Integer page, Integer limit);
	/**
	 * 	查询按钮记录条数
	 * @return
	 */
	Integer queryCount();
	/**
	 * 删除按钮
	 * @param idsArray
	 * @return
	 */
	int deleteBatch(String[] idsArray);
	/**
	 *  	查询所有静态资源按钮权限
	 * @return
	 */
	List<Buttons> queryButtonsPower();
}