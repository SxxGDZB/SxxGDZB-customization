package com.kiwihouse.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.kiwihouse.common.bean.Code;
import com.kiwihouse.dao.entity.AuthResource;
import com.kiwihouse.dao.entity.MenuBtnModel;
import com.kiwihouse.dao.entity.MenuRes;
import com.kiwihouse.dao.entity.MenuResModel;
import com.kiwihouse.dao.mapper.MenuResModelMapper;
import com.kiwihouse.domain.vo.Response;
import com.kiwihouse.service.MenuResModelService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

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

	@Override
	public Map<String, Object> selectMenuNotResList(Integer page, Integer limit, MenuResModel menuResModel) {
		 Map<String, Object> map = new HashMap<String, Object>();
			// TODO Auto-generated method stub
			map.put("count", menuResModelMapper.selectMenuNotResCount(menuResModel));
			List<AuthResource> list = new ArrayList<AuthResource>();
			if (page != null) {
				list = menuResModelMapper.selectMenuNotResList((page - 1)*limit,limit,menuResModel);
			} else {
				list = menuResModelMapper.selectMenuNotResList(null,null,menuResModel);
			}
			map.put("data", list);
		return map;
	}

	@Override
	public Response insertBatchMenuResModel(List<MenuResModel> list) {
		try {
			int count = menuResModelMapper.insertBatchMenuResModel(list);
			if(count > 0) {
				return new Response().Success(Code.ADD_SUCCESS,Code.ADD_SUCCESS.getMsg());
			}
			return new Response().Fail(Code.ADD_FAIL,Code.ADD_FAIL.getMsg());
		} catch (Exception e) {
			// TODO: handle exception
			return new Response().Fail(Code.ADD_FAIL,Code.ADD_FAIL.getMsg());
		}
	}

	@Override
	public Response deleteBatchMenuResModel(String ids) {
		// TODO Auto-generated method stub
		try {
			
			int count = menuResModelMapper.deleteBatchMenuResModel(ids.split(","));
			if(count > 0) {
				return new Response().Success(Code.DELETE_SUCCESS,Code.DELETE_SUCCESS.getMsg());
			}
			return new Response().Fail(Code.DELETE_FAIL,Code.DELETE_FAIL.getMsg());
		} catch (Exception e) {
			// TODO: handle exception
			return new Response().Fail(Code.DELETE_FAIL,Code.DELETE_FAIL.getMsg());
		}
	}

	@Override
	public Response resModelPageByRoleId(Integer roleId) {
		// TODO Auto-generated method stub
		List<MenuRes> menuResList = menuResModelMapper.resModelPageByRoleId(roleId);
		Map<String,List<MenuRes>> map = new HashMap<String, List<MenuRes>>();
		menuResList.forEach(xx ->{
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

}
