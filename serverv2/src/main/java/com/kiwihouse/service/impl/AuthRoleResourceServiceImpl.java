package com.kiwihouse.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kiwihouse.dao.entity.AuthRoleMenu;
import com.kiwihouse.dao.entity.AuthRoleResource;
import com.kiwihouse.dao.mapper.AuthRoleResourceMapper;
import com.kiwihouse.domain.vo.AuthRoleResourceVo;
import com.kiwihouse.domain.vo.AuthRoleMenuDetails;
import com.kiwihouse.service.AuthRoleResourceService;

@Service
public class AuthRoleResourceServiceImpl implements AuthRoleResourceService{

	@Autowired
	AuthRoleResourceMapper authRoleResourceMapper;

	@Override
	public Map<String, Object> getRoleResourceList(Integer page, Integer limit, Integer roleId,
			AuthRoleResourceVo auRoleResourceVo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("count", authRoleResourceMapper.selectRoleResourceCount(roleId,auRoleResourceVo));
		List<AuthRoleMenuDetails> list = new ArrayList<AuthRoleMenuDetails>();
		System.out.println(auRoleResourceVo.getType());
		if (page != null) {
			list = authRoleResourceMapper.selectRoleResourceList((page - 1) * limit,limit,roleId,auRoleResourceVo);
		} else {
			list = authRoleResourceMapper.selectRoleResourceList(null,null,roleId,auRoleResourceVo);
		}
		map.put("data", list);
		return map;
	}

	@Override
	public Boolean addAuthRoleResource(AuthRoleResourceVo auResourceVo) {
		// TODO Auto-generated method stub
		String [] arr = auResourceVo.getResIds().split(",");
		// TODO Auto-generated method stub
		List<AuthRoleResource> list = new ArrayList<AuthRoleResource>();
		for(String roleId : arr) {
			AuthRoleResource authRoleResource = new AuthRoleResource();
			authRoleResource.setRoleId(auResourceVo.getRoleId());
			authRoleResource.setResourceId(Integer.valueOf(roleId));
			list.add(authRoleResource);
		}
		int num = authRoleResourceMapper.insertBatch(list);
		 return num > 0 ? Boolean.TRUE : Boolean.FALSE;
	}

	@Override
	public Boolean deleteBatch(String ids) {
		String [] idsArr = ids.split(",");
		// TODO Auto-generated method stub
		
		int num = authRoleResourceMapper.deleteBatch(idsArr);
		 return num > 0 ? Boolean.TRUE : Boolean.FALSE;
	}

	@Override
	public Boolean updAuthRoleResource(Integer roleId, String resIds) {
		authRoleResourceMapper.deleteByRoleId(roleId);
		
		String [] resArr = resIds.split(",");
		// TODO Auto-generated method stub
		List<AuthRoleResource> list = new ArrayList<AuthRoleResource>();
		for(int i=0;i<resArr.length; i++) {
			AuthRoleResource authRoleResource = new AuthRoleResource();
			authRoleResource.setRoleId(roleId);
			authRoleResource.setResourceId(Integer.valueOf(resArr[i]));
			list.add(authRoleResource);
		}
		int num = authRoleResourceMapper.insertOrUpdateBatch(list);
		return num > 0 ? Boolean.TRUE : Boolean.FALSE;
	}
}
