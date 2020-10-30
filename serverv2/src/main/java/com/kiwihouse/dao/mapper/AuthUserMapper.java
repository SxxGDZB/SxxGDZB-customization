package com.kiwihouse.dao.mapper;

import com.kiwihouse.dao.entity.AuthUser;

import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.Map;

/**
 * @author tomsun28
 * @date 10:35 2018/4/22
 */
public interface AuthUserMapper {

    /**
     * description TODO
     *
     * @param uid 1
     * @return int
     * @throws DataAccessException when
     */
    int deleteByPrimaryKey(Integer uid) throws DataAccessException;

    /**
     * description TODO
     *
     * @param record 1
     * @return int
     * @throws DataAccessException when
     */
    int insert(AuthUser record) throws DataAccessException;

    /**
     * description TODO
     *
     * @param record 1
     * @return int
     * @throws DataAccessException when
     */
    int insertSelective(AuthUser record) throws DataAccessException;

    /**
     * description TODO
     *
     * @param uid 1
     * @return com.kiwihouse.dao.entity.AuthUser
     * @throws DataAccessException when
     */
    AuthUser selectByPrimaryKey(Integer uid) throws DataAccessException;

    /**
     * description TODO
     *
     * @param uid 1
     * @return com.kiwihouse.dao.entity.AuthUser
     * @throws DataAccessException when
     */
    AuthUser selectByUsername(String username) throws DataAccessException;

    /**
     * description TODO
     *
     * @param record 1
     * @return int
     * @throws DataAccessException when
     */
    int updateByPrimaryKeySelective(AuthUser record) throws DataAccessException;

    /**
     * description TODO
     *
     * @param record 1
     * @return int
     * @throws DataAccessException when
     */
    int updateByPrimaryKey(AuthUser record) throws DataAccessException;

    /**
     * description TODO
     *
     * @param uid 1
     * @return java.lang.String
     * @throws DataAccessException when
     */
    String selectUserRoles(Integer uid) throws DataAccessException;


    /**
     * description TODO
     *
     * @param username 1
     * @return java.lang.String
     * @throws DataAccessException when
     */
    String selectUserRolesByUsername(String username) throws DataAccessException;


    /**
     * description TODO
     *
     * @return java.util.List<com.kiwihouse.dao.entity.AuthUser>
     * @throws DataAccessException when
     */
    List<AuthUser> selectUserList() throws DataAccessException;

    /**
     * description TODO
     *
     * @param roleId 1
     * @return java.util.List<com.kiwihouse.dao.entity.AuthUser>
     * @throws DataAccessException when
     */
    List<AuthUser> selectUserListByRoleId(Integer roleId) throws DataAccessException;

    /**
     * description TODO
     *
     * @param roleId 1
     * @return java.util.List<com.kiwihouse.dao.entity.AuthUser>
     * @throws DataAccessException when
     */
    List<AuthUser> selectUserListExtendByRoleId(Integer roleId) throws DataAccessException;
    /**
     * 修改用户-判断该用户是否属于管理员用户
     * @param adminId
     * @return
     */
	List<String> queryUserIds(String adminId);
	/**
	 * 	根据userId、roleId获取用户可以查询的用户信息
	 * @param userId
	 * @param roleId
	 * @return
	 */
	List<Map<String, Integer>> queryAuthUserByRoleUserId(Integer userId, Integer roleId);
	/**
	 * 	用户联系人
	 * @param userPhone
	 * @return
	 */
	List<String> queryCtsPhone(String userPhone);
	
	/**
	 * 	查询最大ID
	 */
	Integer selectMaxId();
	/**
	 * 	下级角色的用户列表
	 * @param i
	 * @param limit
	 * @param roleId
	 * @param adminId 
	 * @return
	 */
	List<AuthUser> getList(Integer page, Integer limit, Integer roleId, Integer adminId);
	/**
	 * 	下级角色的用户记录数
	 * @param roleId
	 * @param adminId 
	 * @return
	 */
	int getListCount(Integer roleId, Integer adminId);
}