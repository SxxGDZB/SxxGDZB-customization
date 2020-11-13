package com.kiwihouse.dao.mapper;

import com.kiwihouse.dao.entity.AuthUserRole;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

/**
 * @author tomsun28
 * @date 11:23 2018/4/22
 */
@Component
public interface AuthUserRoleMapper {

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
    int insert(AuthUserRole record) throws DataAccessException;

    /**
     * description TODO
     *
     * @param record 1
     * @return int
     * @throws DataAccessException when
     */
    int insertSelective(AuthUserRole record) throws DataAccessException;

    /**
     * description TODO
     *
     * @param id 1
     * @return com.kiwihouse.dao.entity.AuthUserRole
     * @throws DataAccessException when
     */
    AuthUserRole selectByPrimaryKey(Integer id) throws DataAccessException;

    /**
     * description TODO
     *
     * @param record 1
     * @return int
     * @throws DataAccessException when
     */
    int updateByPrimaryKeySelective(AuthUserRole record) throws DataAccessException;

    /**
     * description TODO
     *
     * @param record 1
     * @return int
     * @throws DataAccessException when
     */
    int updateByPrimaryKey(AuthUserRole record) throws DataAccessException;

    /**
     * description TODO
     *
     * @param record 1
     * @return int
     * @throws DataAccessException when
     */
    int deleteByUniqueKey(AuthUserRole record) throws DataAccessException;
    /**
     * 根据用户id。查询roleId
     * @param uid
     * @return
     */
    AuthUserRole selectByUid(Integer uid);
    /**
     * 修改用户角色关联表
     * @param uid
     * @param roleId
     */
	void updateByUniqueKey(Integer uid, Integer roleId);
}