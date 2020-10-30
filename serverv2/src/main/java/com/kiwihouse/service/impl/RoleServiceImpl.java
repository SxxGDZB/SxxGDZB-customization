package com.kiwihouse.service.impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.kiwihouse.common.bean.DataType;
import com.kiwihouse.dao.entity.AuthRole;
import com.kiwihouse.dao.entity.AuthRoleMenu;
import com.kiwihouse.dao.entity.AuthRoleResource;
import com.kiwihouse.dao.entity.AuthUser;
import com.kiwihouse.dao.mapper.AuthRoleMapper;
import com.kiwihouse.dao.mapper.AuthRoleMenuMapper;
import com.kiwihouse.dao.mapper.AuthRoleResourceMapper;
import com.kiwihouse.dao.mapper.AuthUserMapper;
import com.kiwihouse.service.RoleService;

/**
 * @author tomsun28
 * @date 12:28 2018/3/26
 */
@Service("RoleService")
public class RoleServiceImpl implements RoleService {

    @Autowired
    private AuthRoleResourceMapper authRoleResourceMapper;

    @Autowired
    private AuthRoleMapper authRoleMapper;

    @Autowired
    private AuthRoleMenuMapper authRoleMenuMapper;
    @Autowired
    AuthUserMapper authUserMapper;
    @Override
    public boolean authorityRoleResource(int roleId, int resourceId) throws DataAccessException {
        AuthRoleResource authRoleResource = new AuthRoleResource();
        authRoleResource.setRoleId(roleId);
        authRoleResource.setResourceId(resourceId);
        authRoleResource.setCreateTime(new Date());
        authRoleResource.setUpdateTime(new Date());
        return authRoleResourceMapper.insert(authRoleResource) == 1? Boolean.TRUE : Boolean.FALSE;
    }

    @Override
    public boolean addRole(AuthRole role) throws DataAccessException {
        int num = authRoleMapper.insertSelective(role);
        if(num > 0) {
        	System.out.println("添加成功初始化按钮权限---------------->" + role.getId());
        	authRoleMapper.initBtn(role.getId());
        	//初始化菜单
        	List<AuthRoleMenu> list = new ArrayList<AuthRoleMenu>();
        	String [] menuIds = role.getMenuIds().split(",");
        	for(int i = 0 ;i<menuIds.length;i++) {
        		AuthRoleMenu authRoleMenu = new AuthRoleMenu(role.getId(), Integer.valueOf(menuIds[i]));
        		list.add(authRoleMenu);
        	}
        	authRoleMenuMapper.insertBatch(list);
        }
        return num == 1? Boolean.TRUE : Boolean.FALSE;
    }

    @Override
    public boolean updateRole(AuthRole role) throws DataAccessException {
        int num = authRoleMapper.updateByPrimaryKeySelective(role);
        if(num > 0) {
        	authRoleMenuMapper.deleteByRole(role.getId());
        	List<AuthRoleMenu> list = new ArrayList<AuthRoleMenu>();
        	String [] menuIds = role.getMenuIds().split(",");
        	for(int i = 0 ;i<menuIds.length;i++) {
        		AuthRoleMenu authRoleMenu = new AuthRoleMenu(role.getId(), Integer.valueOf(menuIds[i]));
        		list.add(authRoleMenu);
        	}
        	authRoleMenuMapper.insertBatch(list);
        }
        return num == 1? Boolean.TRUE : Boolean.FALSE;
    }

    @Override
    public boolean deleteRoleByRoleId(Integer roleId) throws DataAccessException {
        int num = authRoleMapper.deleteByPrimaryKey(roleId);
        authRoleMenuMapper.deleteByRole(roleId);
        return num == 1? Boolean.TRUE : Boolean.FALSE;
    }

    @Override
    public boolean deleteAuthorityRoleResource(Integer roleId, Integer resourceId) throws DataAccessException {
        int num = authRoleResourceMapper.deleteByUniqueKey(roleId, resourceId);
        return num == 1? Boolean.TRUE : Boolean.FALSE;
    }

    @Override
    public List<AuthRole> getRoleList() throws DataAccessException {
        return authRoleMapper.selectRoles();
    }

	@Override
	public List<AuthRole> queryAuthRole(Integer roleId,String oneself,Integer userId) {
		// TODO Auto-generated method stub
		List<AuthRole> lists = new ArrayList<AuthRole>();
		List<AuthRole> list = authRoleMapper.queryAuthRole(roleId);
		AuthUser auth = authUserMapper.selectByPrimaryKey(userId);
		list.forEach(xx ->{
			if(oneself == null && !auth.getUsername().equals(DataType.admin)) {
				if(xx.getId() != roleId) {
					lists.add(xx);
				}
			}else {
				lists.add(xx);
			}
			
		});
		return lists;
	}

	@Override
	public Map<String, Object> getSelectRolesList(Integer page, Integer limit,Integer roleId) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		List<AuthRole> list = null;
		int count = authRoleMapper.getSelectRolesListCount(roleId);
		if(limit != null) {
			list = authRoleMapper.getSelectRolesList((page - 1 ) * limit,limit,roleId);
		}else {
			list = authRoleMapper.getSelectRolesList(null,null,roleId);
		}
		map.put("data", list);
		map.put("count", count);
		return map;
	}

	@Override
	public List<AuthUser> queryAuthUserByUserId(Integer roleId) {
		// TODO Auto-generated method stub
		
		List<AuthUser> listUser =  authRoleMapper.queryAuthUserByUserId(roleId);
		
		return listUser;
	}
}
