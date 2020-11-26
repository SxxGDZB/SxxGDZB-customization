package com.kiwihouse.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kiwihouse.common.bean.Code;
import com.kiwihouse.dao.entity.MenuBtnModel;
import com.kiwihouse.dao.entity.MenuRes;
import com.kiwihouse.dao.mapper.MenuResMapper;
import com.kiwihouse.domain.vo.Response;
import com.kiwihouse.service.MenuResService;
@Service
public class MenuResServiceImpl implements MenuResService{

	@Autowired
	MenuResMapper menuResMapper;
	
	@Override
	public Response resPageByRoleId(Integer roleId) {
		// TODO Auto-generated method stub
		List<MenuRes> menuResList = menuResMapper.resPageByRoleId(roleId);
		Map<String,List<MenuRes>> map = new HashMap<String, List<MenuRes>>();
		menuResList.forEach(xx ->{
			System.out.println(xx.toString());
			List<MenuRes> list = null;
			String key = xx.getMenuName() + "_" + xx.getMenuId();
			if(map.containsKey(key)) {
				list = map.get(key);
				list.add(xx);
				map.put(key, list);
			}else {
				list = new ArrayList<MenuRes>();
				list.add(xx);
				map.put(key, list);
			}
		});
		return new Response().Success(Code.QUERY_SUCCESS,Code.QUERY_SUCCESS.getMsg()).addData("data", map);
	}

	@Override
	public Response menuResUpdBatch(List<MenuRes> list, Integer roleId) {
		// TODO Auto-generated method stub
		list.forEach(xx ->{
			xx.setRoleId(roleId);
		});
		menuResMapper.delMenuResByRoleId(roleId);
		menuResMapper.insertBatch(list);
		return new Response().Success(Code.ADD_SUCCESS,Code.ADD_SUCCESS.getMsg());
	}

}
