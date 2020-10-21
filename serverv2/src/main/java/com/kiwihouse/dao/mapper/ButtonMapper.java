package com.kiwihouse.dao.mapper;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.kiwihouse.dao.entity.Buttons;
import com.kiwihouse.dao.entity.MenuBtnModel;

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
     * @param trigger 
     * @return
     */
	List<Buttons> queryInfo(@Param("page") Integer page, @Param("limit") Integer limit,@Param("trigger") Integer trigger,@Param("buttons") Buttons buttons);
	/**
	 * 	查询按钮记录条数
	 * @return
	 */
	Integer queryCount(@Param("trigger") Integer trigger,@Param("buttons") Buttons buttons);
	/**
	 * 删除按钮
	 * @param idsArray
	 * @return
	 */
	int deleteBatch(String[] idsArray);
	/**
	 *  	查询所有静态资源按钮权限
	 * @param roleId 
	 * @return
	 */
	List<MenuBtnModel> queryButtonsPower(Integer roleId);
}