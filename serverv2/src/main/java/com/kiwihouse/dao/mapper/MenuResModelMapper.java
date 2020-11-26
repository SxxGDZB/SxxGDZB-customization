package com.kiwihouse.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.kiwihouse.dao.entity.AuthResource;
import com.kiwihouse.dao.entity.MenuRes;
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
	/**
	 * 	菜单资源模板不存在的资源列表条数
	 * @param menuResModel
	 * @return
	 */
	int selectMenuNotResCount(MenuResModel menuResModel);
	/**
	 * 	菜单资源模板不存在的资源列表查询
	 * @param i
	 * @param limit
	 * @param menuResModel
	 * @return
	 */
	List<AuthResource> selectMenuNotResList(@Param("page") Integer page, @Param("limit") Integer limit, @Param("menuResModel") MenuResModel menuResModel);
	/**
	 * 	添加菜单模板资源
	 * @param list
	 * @return
	 */
	int insertBatchMenuResModel(List<MenuResModel> list);
	/**
	 * 	删除菜单资源模板
	 * @param strings
	 * @return
	 */
	int deleteBatchMenuResModel(String[] ids);
	/**
	 * 	根据RoleID获取静态页面以及对应的资源列表模板
	 * @param roleId
	 * @return
	 */
	List<MenuRes> resModelPageByRoleId(Integer roleId);
	/**
	 * 	根据菜单ID查询菜单资源模板
	 * @param menuIds
	 * @return
	 */
	List<MenuRes> queryMenuResByMenuIds(String[] menuIds);
}