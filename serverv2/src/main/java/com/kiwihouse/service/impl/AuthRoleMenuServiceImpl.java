package com.kiwihouse.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kiwihouse.dao.entity.AuthRoleMenu;
import com.kiwihouse.dao.mapper.AuthRoleMenuMapper;
import com.kiwihouse.domain.vo.AuthRoleMenuDetails;
import com.kiwihouse.service.AuthRoleMenuService;
@Service
public class AuthRoleMenuServiceImpl implements AuthRoleMenuService{
	@Autowired
	AuthRoleMenuMapper authRoleMenuMapper;

	@Override
	public Map<String, Object> selectPage(Integer currentPage, Integer pageSize, Integer roleId,Integer curRoleId,AuthRoleMenuDetails authRoleMenuDetails,Integer trigger) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("count", authRoleMenuMapper.selectRoleMenuCount(roleId,curRoleId,authRoleMenuDetails, trigger));
		List<AuthRoleMenuDetails> list = null;
		if (currentPage != null) {
			list = authRoleMenuMapper.selectRoleMenuList((currentPage - 1) * pageSize,pageSize,roleId,curRoleId,authRoleMenuDetails,trigger);
		} else {
			list = authRoleMenuMapper.selectRoleMenuList(null,null,roleId,curRoleId,authRoleMenuDetails,trigger);
		}
		map.put("data", list);
		return map;
	}

	@Override
	public boolean insertBatch(Integer roleId, String ids) {
		String [] arr = ids.split(",");
		// TODO Auto-generated method stub
		List<AuthRoleMenu> list = new ArrayList<AuthRoleMenu>();
		for(String menuId : arr) {
			AuthRoleMenu authRoleMenu = new AuthRoleMenu();
			authRoleMenu.setRoleId(roleId);
			authRoleMenu.setMenuId(Integer.valueOf(menuId));
			list.add(authRoleMenu);
		}
		int num = authRoleMenuMapper.insertBatch(list);
		 return num > 0 ? Boolean.TRUE : Boolean.FALSE;
	}

	@Override
	public boolean update(AuthRoleMenu authRoleMenu) {
		try {
			//先删除
			authRoleMenuMapper.deleteByRole(authRoleMenu.getRoleId());
			//后添加
			String [] arr = authRoleMenu.getIds().split(",");
			// TODO Auto-generated method stub
			if(arr.length > 0) {
				List<AuthRoleMenu> list = new ArrayList<AuthRoleMenu>();
				for(String menuId : arr) {
					AuthRoleMenu authRM = new AuthRoleMenu();
					authRM.setRoleId(authRoleMenu.getRoleId());
					authRM.setMenuId(Integer.valueOf(menuId));
					list.add(authRM);
				}
				int num = authRoleMenuMapper.insertBatch(list);
				return num > 0 ? Boolean.TRUE : Boolean.FALSE;
			}else {
				return Boolean.TRUE;
			}
		} catch (Exception e) {
			// TODO: handle exception
			return Boolean.FALSE;
		}
	}

	@Override
	public boolean deleteRoleMenuByRoleMenuId(String roleIds, String menuIds) {
		String [] arr = menuIds.split(",");
		String [] roleArr = roleIds.split(",");
		// TODO Auto-generated method stub
		List<AuthRoleMenu> list = new ArrayList<AuthRoleMenu>();
		for(int i=0;i<arr.length; i++) {
			AuthRoleMenu authRoleMenu = new AuthRoleMenu();
			authRoleMenu.setRoleId(Integer.valueOf(roleArr[i]));
			authRoleMenu.setMenuId(Integer.valueOf(arr[i]));
			list.add(authRoleMenu);
		}
		int num = authRoleMenuMapper.deleteBatch(list);
		return num > 0 ? Boolean.TRUE : Boolean.FALSE;
	}

	@Override
	public int deleteBatch(String[] idsStrArr) {
		// TODO Auto-generated method stub
		return  authRoleMenuMapper.deleteBatchByMenuId(idsStrArr);
	}

	@Override
	public void deleteBatchByMenuId(String[] ids) {
		// TODO Auto-generated method stub
		int num =  authRoleMenuMapper.deleteBatchByMenuId(ids);
	}
}
