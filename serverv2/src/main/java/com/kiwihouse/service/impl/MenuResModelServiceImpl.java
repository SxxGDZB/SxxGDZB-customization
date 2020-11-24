package com.kiwihouse.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kiwihouse.dao.entity.AuthResource;
import com.kiwihouse.dao.entity.MenuResModel;
import com.kiwihouse.dao.mapper.MenuResModelMapper;
import com.kiwihouse.service.MenuResModelService;

@Service
public class MenuResModelServiceImpl implements MenuResModelService{

	@Autowired
	MenuResModelMapper menuResModelMapper;
	
	@Override
	public Map<String, Object> selectAllPage(Integer page, Integer limit, MenuResModel menuResModel) {
		 Map<String, Object> map = new HashMap<String, Object>();
		// TODO Auto-generated method stub
		map.put("count", menuResModelMapper.selectMenuResModelCount(menuResModel));
		List<AuthResource> list = new ArrayList<AuthResource>();
		if (page != null) {
			list = menuResModelMapper.selectMenuResModelList((page - 1)*limit,limit,menuResModel);
		} else {
			list = menuResModelMapper.selectMenuResModelList(null,null,menuResModel);
		}
		map.put("data", list);
		return map;
	}

}
