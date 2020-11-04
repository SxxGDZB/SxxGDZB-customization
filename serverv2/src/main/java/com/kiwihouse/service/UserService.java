package com.kiwihouse.service;


import com.kiwihouse.dao.entity.AuthUser;
import com.kiwihouse.domain.vo.Response;

import java.util.List;
import java.util.Map;

/**
 * @author tomsun28
 * @date 21:14 2018/3/17
 */
public interface UserService {

    /**
     * description TODO
     *
     * @param appId 1
     * @return java.lang.String
     */
    String loadAccountRole(Integer appId);

    /**
     * description TODO
     *
     * @return java.util.List<com.kiwihouse.dao.entity.AuthUser>
     */
    List<AuthUser> getUserList();

    /**
     * description TODO
     *
     * @param roleId 1
     * @return java.util.List<com.kiwihouse.dao.entity.AuthUser>
     */
    List<AuthUser> getUserListByRoleId(Integer roleId);

    /**
     * description TODO
     *
     * @param appId  1
     * @param roleId 2
     * @return boolean
     */
    boolean authorityUserRole(Integer uid, int roleId);

    /**
     * description TODO
     *
     * @param uid    1
     * @param roleId 2
     * @return boolean
     */
    boolean deleteAuthorityUserRole(Integer uid, int roleId);

    /**
     * description TODO
     *
     * @param uid 1
     * @return com.kiwihouse.dao.entity.AuthUser
     */
    AuthUser getUserByUid(Integer uid);

    /**
     * description TODO
     *
     * @param username 1
     * @return com.kiwihouse.dao.entity.AuthUser
     */
    AuthUser getUserByUsername(String username);

    /**
     * description TODO
     *
     * @param roleId 1
     * @return java.util.List<com.kiwihouse.dao.entity.AuthUser>
     */
    List<AuthUser> getNotAuthorityUserListByRoleId(Integer roleId);
    /**
     * 根据userId、roleId获取用户可以查询的用户信息
     * @param userId
     * @param roleId
     * @return
     */
	List<Map<String, Integer>> queryAuthUserByRoleUserId(Integer userId, Integer roleId);
	/**
	 * 修改用户信息
	 * @param authUser
	 * @return
	 */
	Response updateByPrimaryKeySelective(AuthUser authUser);
	/**
	 * 	查询角色下  用户列表
	 * @param page
	 * @param limit
	 * @param roleId
	 * @return
	 */
	Map<String, Object> getList(Integer page, Integer limit, Integer roleId,Integer userId);
	/**
	 * 	添加用户
	 * @param authUser
	 * @return
	 */
	Response insert(AuthUser authUser);
	/**
	 * 	删除用户
	 * @param userIds
	 * @return
	 */
	Response deleteBatch(String userIds);
}
