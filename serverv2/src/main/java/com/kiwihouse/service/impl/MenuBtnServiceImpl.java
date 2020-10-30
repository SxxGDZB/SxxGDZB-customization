package com.kiwihouse.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kiwihouse.common.bean.Code;
import com.kiwihouse.dao.entity.Buttons;
import com.kiwihouse.dao.entity.MenuBtnModel;
import com.kiwihouse.dao.entity.ResBtnModel;
import com.kiwihouse.dao.entity.MenuButton;
import com.kiwihouse.dao.mapper.MenuBtnMapper;
import com.kiwihouse.domain.vo.Response;
import com.kiwihouse.service.MenuBtnService;

@Service
public class MenuBtnServiceImpl implements MenuBtnService{
	@Autowired
	MenuBtnMapper menuBtnMapper;
	@Override
	public Response buttonListById(Integer menuId,Integer roleId) {
		// TODO Auto-generated method stub
		List<Buttons> resourceButtonsList = menuBtnMapper.buttonListById(menuId,roleId);
		resourceButtonsList.forEach(xx ->{
			System.out.println(xx.toString());
		});
		return new Response().Success(Code.QUERY_SUCCESS,Code.QUERY_SUCCESS.getMsg()).addData("data", resourceButtonsList);
	}
	@Override
	public Response insertOrUpdateBatch(List<MenuBtnModel> resourceButtons,Integer roleId) {
		// TODO Auto-generated method stub
		resourceButtons.forEach(xx ->{
			xx.setRoleId(roleId);
		});
		menuBtnMapper.delete(roleId);
		menuBtnMapper.insertOrUpdateBatch(resourceButtons);
		return new Response().Success(Code.QUERY_SUCCESS,Code.QUERY_SUCCESS.getMsg());
	}
	
	@Override
	public Map<String, Object> queryInfo(Integer page, Integer limit,Integer roleId) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
//		try {
			List<ResBtnModel> resourceButtonsList = null;
			if(limit != null) {
				resourceButtonsList =  menuBtnMapper.queryInfo((page - 1) * limit,  limit,roleId);
			}else {
				resourceButtonsList =  menuBtnMapper.queryInfo(null,  null,roleId);
			}
			Integer count = menuBtnMapper.queryCount(roleId);
			if(resourceButtonsList != null) {
				map.put("data", resourceButtonsList);
				map.put("count", count);
			}
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
		return map;
	}
	@Override
	public Response addModelBtn(List<MenuBtnModel> list) {
		// TODO Auto-generated method stub
		try {
			int count = menuBtnMapper.addModelBtn(list);
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
	public Response addOrUpdModelBtn(List<MenuBtnModel> list) {
		// TODO Auto-generated method stub
		try {
			
			int i = menuBtnMapper.deleteModelBtn(list.get(0).getMenuId());
			int count = menuBtnMapper.addOrUpdModelBtn(list);
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
	public Response deleteModelBtn(Integer menuId) {
		// TODO Auto-generated method stub
		try {
			int count = menuBtnMapper.deleteModelBtn(menuId);
			if(count > 0) {
				menuBtnMapper.deleteByMenuId(menuId);
				return new Response().Success(Code.DELETE_SUCCESS,Code.DELETE_SUCCESS.getMsg());
			}
			return new Response().Fail(Code.DELETE_FAIL,Code.DELETE_FAIL.getMsg());
		} catch (Exception e) {
			// TODO: handle exception
			return new Response().Fail(Code.DELETE_FAIL,Code.DELETE_FAIL.getMsg());
		}
	}
	
}
