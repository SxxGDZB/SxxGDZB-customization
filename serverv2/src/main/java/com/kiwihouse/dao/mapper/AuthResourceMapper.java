package com.kiwihouse.dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import com.kiwihouse.dao.entity.AuthResource;
import com.kiwihouse.shiro.rule.RolePermRule;

/**
 * @author tomsun28
 * @date 9:28 2018/4/22
 */
@Component
public interface AuthResourceMapper {
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
    int insert(AuthResource record) throws DataAccessException;

    /**
     * description TODO
     *
     * @param record 1
     * @return int
     * @throws DataAccessException when
     */
    int insertSelective(AuthResource record) throws DataAccessException;

    /**
     * description TODO
     *
     * @param id 1
     * @return com.kiwihouse.dao.entity.AuthResource
     * @throws DataAccessException when
     */
    AuthResource selectByPrimaryKey(Integer id) throws DataAccessException;

    /**
     * description TODO
     *
     * @param record 1
     * @return int
     * @throws DataAccessException when
     */
    int updateByPrimaryKeySelective(AuthResource record) throws DataAccessException;

    /**
     * description TODO
     *
     * @param record 1
     * @return int
     * @throws DataAccessException when
     */
    int updateByPrimaryKey(AuthResource record) throws DataAccessException;

    /**
     * description TODO
     *
     * @return java.util.List<com.kiwihouse.shiro.rule.RolePermRule>
     * @throws DataAccessException when
     */
    List<RolePermRule> selectRoleRules()  throws DataAccessException;

    /**
     * description TODO
     *
     * @param appId 1
     * @return java.util.List<com.kiwihouse.dao.entity.AuthResource>
     * @throws DataAccessException when
     */
    List<AuthResource> selectAuthorityMenusByUid(String appId) throws DataAccessException;

    /**
     * description TODO
     *
     * @return java.util.List<com.kiwihouse.dao.entity.AuthResource>
     * @throws DataAccessException when
     */
    List<AuthResource> selectMenus() throws DataAccessException;

    /**
     * description TODO
     *
     * @return java.util.List<com.kiwihouse.dao.entity.AuthResource>
     * @throws DataAccessException when
     */
    List<AuthResource> selectApiTeamList() throws DataAccessException;

    /**
     * description TODO
     * @return java.util.List<com.kiwihouse.dao.entity.AuthResource>
     * @throws DataAccessException when
     */
    List<AuthResource> selectApiList() throws DataAccessException;

    /**
     * description TODO
     *
     * @param teamId 1
     * @return java.util.List<com.kiwihouse.dao.entity.AuthResource>
     * @throws DataAccessException when
     */
    List<AuthResource> selectApiListByTeamId(Integer teamId) throws DataAccessException;

    /**
     * description TODO
     *
     * @param roleId 1
     * @return java.util.List<com.kiwihouse.dao.entity.AuthResource>
     * @throws DataAccessException when
     */
    List<AuthResource> selectApisByRoleId(Integer roleId) throws DataAccessException;

    /**
     * description TODO
     *
     * @param roleId 1
     * @return java.util.List<com.kiwihouse.dao.entity.AuthResource>
     * @throws DataAccessException when
     */
    List<AuthResource> selectMenusByRoleId(Integer roleId) throws DataAccessException;

    /**
     * description TODO
     *
     * @param roleId 1
     * @return java.util.List<com.kiwihouse.dao.entity.AuthResource>
     * @throws DataAccessException when
     */
    List<AuthResource> selectNotAuthorityApisByRoleId(Integer roleId) throws DataAccessException;

    /**
     * description TODO
     *
     * @param roleId 1
     * @return java.util.List<com.kiwihouse.dao.entity.AuthResource>
     * @throws DataAccessException when
     */
    List<AuthResource> selectNotAuthorityMenusByRoleId(Integer roleId) throws DataAccessException;
    /**
     * 查询资源记录总数
     * @param roleId
     * @return
     */
	Integer selectAuthResourceCount(
			@Param("roleId") Integer roleId,
			@Param("auResource") AuthResource auResource);
	/**
	 * 
	 * @param page
	 * @param limit
	 * @return
	 */
	List<AuthResource> selectAuthResourceList(
			@Param("page") Integer page, 
			@Param("limit") Integer limit,
			@Param("roleId") Integer roleId,
			@Param("auResource") AuthResource auResource);
	/**
	 * 批量删除
	 * @param menuIdArr
	 * @return
	 */
	int deleteBatch(String[] menuIdArr);
	/**
	 * 查询静态资源列表
	 * @return
	 */
	List<AuthResource> selectStaticResource();
}