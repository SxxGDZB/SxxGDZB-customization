package com.kiwihouse.service.impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.kiwihouse.common.bean.Code;
import com.kiwihouse.dao.entity.AuthResource;
import com.kiwihouse.dao.mapper.AuthResourceMapper;
import com.kiwihouse.domain.vo.Response;
import com.kiwihouse.service.ResourceService;

/**
 * @author tomsun28
 * @date 10:59 2018/3/26
 */
@Service("ResourceService")
public class ResourceServiceImpl implements ResourceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceServiceImpl.class);

    @Autowired
    private AuthResourceMapper authResourceMapper;

    @Override
    public List<AuthResource> getAuthorityMenusByUid(String appId) throws DataAccessException {
        return authResourceMapper.selectAuthorityMenusByUid(appId);
    }

    @Override
    public List<AuthResource> getMenus() throws DataAccessException {
        return authResourceMapper.selectMenus();
    }

    @Override
    public Boolean addMenu(AuthResource menu) throws DataAccessException {
        int num = authResourceMapper.insertSelective(menu);
        return num == 1 ? Boolean.TRUE : Boolean.FALSE;
    }

    @Override
    public Boolean modifyMenu(AuthResource menu) throws DataAccessException {
        int num = authResourceMapper.updateByPrimaryKeySelective(menu);
        return num == 1 ? Boolean.TRUE : Boolean.FALSE;
    }

    @Override
    public Boolean deleteMenuByMenuId(Integer menuId) throws DataAccessException {
        int num = authResourceMapper.deleteByPrimaryKey(menuId);
        return num == 1 ? Boolean.TRUE : Boolean.FALSE;
    }

    @Override
    public List<AuthResource> getApiTeamList() throws DataAccessException {
        return authResourceMapper.selectApiTeamList();
    }

    @Override
    public List<AuthResource> getApiList() throws DataAccessException {
        return authResourceMapper.selectApiList();
    }

    @Override
    public List<AuthResource> getApiListByTeamId(Integer teamId) throws DataAccessException {
        return authResourceMapper.selectApiListByTeamId(teamId);
    }

    @Override
    public List<AuthResource> getAuthorityApisByRoleId(Integer roleId) throws DataAccessException {
        return authResourceMapper.selectApisByRoleId(roleId);
    }

    @Override
    public List<AuthResource> getAuthorityMenusByRoleId(Integer roleId) throws DataAccessException {
        return authResourceMapper.selectMenusByRoleId(roleId);
    }

    @Override
    public List<AuthResource> getNotAuthorityApisByRoleId(Integer roleId) throws DataAccessException {
        return authResourceMapper.selectNotAuthorityApisByRoleId(roleId);
    }

    @Override
    public List<AuthResource> getNotAuthorityMenusByRoleId(Integer roleId) throws DataAccessException {
        return authResourceMapper.selectNotAuthorityMenusByRoleId(roleId);
    }
    Map<String, Object> map = new HashMap<String, Object>();
    
	@Override
	public Map<String, Object> selectPage(Integer page, Integer limit,Integer roleId,AuthResource authResource) {
		// TODO Auto-generated method stub
		map.put("count", authResourceMapper.selectAuthResourceCount(roleId,authResource));
		List<AuthResource> list = new ArrayList<AuthResource>();
		if (page != null) {
			list = authResourceMapper.selectAuthResourceList((page - 1)*limit,limit,roleId,authResource);
		} else {
			list = authResourceMapper.selectAuthResourceList(null,null,roleId,authResource);
		}
		map.put("data", list);
		return map;
	}

	@Override
	public Boolean deleteBatch(String menuIds) {
		String [] menuIdArr = menuIds.split(",");
		int num = authResourceMapper.deleteBatch(menuIdArr);
        return num > 0 ? Boolean.TRUE : Boolean.FALSE;
	}

	@Override
	public Response selectStaticResource() {
		// TODO Auto-generated method stub
		List<AuthResource> list = null;
		try {
			list = authResourceMapper.selectStaticResource();
			if(list == null) {
				return new Response().Success(Code.QUERY_NULL,Code.QUERY_NULL.getMsg()).addData("data", list);
			}
			return new Response().Success(Code.QUERY_SUCCESS,Code.QUERY_SUCCESS.getMsg()).addData("data", list);
		} catch (Exception e) {
			// TODO: handle exception
			return new Response().Success(Code.QUERY_FAIL,Code.QUERY_FAIL.getMsg());
		}
	}

}
