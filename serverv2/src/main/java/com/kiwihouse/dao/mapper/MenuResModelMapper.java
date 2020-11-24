package com.kiwihouse.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.kiwihouse.dao.entity.AuthResource;
import com.kiwihouse.dao.entity.MenuResModel;

public interface MenuResModelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MenuResModel record);

    int insertSelective(MenuResModel record);

    MenuResModel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MenuResModel record);

    int updateByPrimaryKey(MenuResModel record);
    /**
     *	查询菜单资源模板记录数
     * @param menuResModel
     * @return
     */
	int selectMenuResModelCount(MenuResModel menuResModel);
	/**
	 * 	查询菜单资源模板
	 * @param i
	 * @param limit
	 * @param menuResModel
	 * @return
	 */
	List<AuthResource> selectMenuResModelList(@Param("page") Integer page, @Param("limit") Integer limit, @Param("menuResModel") MenuResModel menuResModel);
}