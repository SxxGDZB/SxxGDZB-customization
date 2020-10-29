package com.kiwihouse.dao.mapper;

import com.kiwihouse.dao.entity.AuthRole;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author tomsun28
 * @date 9:40 2018/4/22
 */
@Component
public interface AuthRoleMapper {
    /**
     * description TODO
     *
     * @param id 1
     * @return int
     * @throws DataAccessException when
     */
    int deleteByPrimaryKey(Integer id) throws DataAccessException;

    /**
     * description TODO
     *
     * @param record 1
     * @return int
     * @throws DataAccessException when
     */
    int insert(AuthRole record) throws DataAccessException;

    /**
     * description TODO
     *
     * @param record 1
     * @return int
     * @throws DataAccessException when
     */
    int insertSelective(AuthRole record) throws DataAccessException;

    /**
     * description TODO
     *
     * @param id 1
     * @return com.kiwihouse.dao.entity.AuthRole
     * @throws DataAccessException when
     */
    AuthRole selectByPrimaryKey(Integer id) throws DataAccessException;

    /**
     * description TODO
     *
     * @param record 1
     * @return int
     * @throws DataAccessException when
     */
    int updateByPrimaryKeySelective(AuthRole record) throws DataAccessException;

    /**
     * description TODO
     *
     * @param record 1
     * @return int
     * @throws DataAccessException when
     */
    int updateByPrimaryKey(AuthRole record) throws DataAccessException;

    /**
     * description TODO
     * @return java.util.List<com.kiwihouse.dao.entity.AuthRole>
     * @throws DataAccessException when
     */
    List<AuthRole> selectRoles() throws DataAccessException;
    /**
     * 	根据roleId 获取其之下的所有角色
     * @param roleId
     * @returnList
     */
    List<AuthRole> queryAuthRole(Integer roleId);
	/**
	 * 	查询角色列表分页
	 * @param i
	 * @param limit
	 * @return
	 */
	List<AuthRole> getSelectRolesList(Integer page, Integer limit);
	/**
	 * 	查询角色列表总数
	 * @return
	 */
	int getSelectRolesListCount();
	/**
	 *	 初始化角色按钮
	 * @param id
	 * @return
	 */
	void initBtn(Integer roleId);
	/**
	 * 	初始化菜单
	 * @param id
	 */
	void initMenu(Integer id);
}