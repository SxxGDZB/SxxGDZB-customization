package com.kiwihouse.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kiwihouse.common.bean.Code;
import com.kiwihouse.controller.button.ButtonController;
import com.kiwihouse.dao.entity.Buttons;
import com.kiwihouse.dao.entity.MenuBtnModel;
import com.kiwihouse.dao.mapper.ButtonMapper;
import com.kiwihouse.domain.vo.Response;
import com.kiwihouse.service.ButtonService;
import com.kiwihouse.vo.entire.Log;

@Service
public class ButtonServiceImpl implements ButtonService{
	private static final Logger logger = LoggerFactory.getLogger(ButtonServiceImpl.class);
	
	@Autowired
	ButtonMapper buttonMapper;
	@Override
	public Map<String, Object> queryInfo(Integer page, Integer limit,Integer trigger,Buttons buttons) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<Buttons> resourceButtonsList = null;
			if(limit != null) {
				resourceButtonsList =  buttonMapper.queryInfo((page - 1) * limit,  limit,trigger,buttons);
			}else {
				resourceButtonsList =  buttonMapper.queryInfo(null,  null,trigger,buttons);
			}
			
			Integer count = buttonMapper.queryCount(trigger,buttons);
			if(resourceButtonsList != null) {
				map.put("data", resourceButtonsList);
				map.put("count", count);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return map;
	}
	@Override
	public Response deleteBatch(String[] idsArray) {
		// TODO Auto-generated method stub
		try {
			int count = buttonMapper.deleteBatch(idsArray);
			if(count > 0) {
				return new Response().Success(Code.DELETE_SUCCESS,Code.DELETE_SUCCESS.getMsg());
			}
			return new Response().Fail(Code.DELETE_FAIL,Code.DELETE_FAIL.getMsg());
		}catch (Exception e) {
			// TODO: handle exception
			return new Response().Success(Code.DELETE_FAIL,Code.DELETE_FAIL.getMsg());
		}
	}
	@Override
	public Response insert(Buttons resourceButtons) {
		// TODO Auto-generated method stub
		try {
			if( buttonMapper.insert(resourceButtons) > 0) {
				return new Response().Success(Code.ADD_SUCCESS,Code.ADD_SUCCESS.getMsg());
			}
			return new Response().Fail(Code.ADD_FAIL,Code.ADD_FAIL.getMsg());
		} catch (Exception e) {
			// TODO: handle exception
			return new Response().Fail(Code.ADD_FAIL, Code.ADD_FAIL.getMsg());
		}
		
		
	}
	@Override
	public Response updateByPrimaryKey(Buttons resourceButtons) {
		// TODO Auto-generated method stub
		try {
			if( buttonMapper.updateByPrimaryKey(resourceButtons) > 0) {
				return new Response().Success(Code.UPDATE_SUCCESS,Code.UPDATE_SUCCESS.getMsg());
			}
			return new Response().Fail(Code.UPDATE_FAIL,Code.UPDATE_FAIL.getMsg());
		} catch (Exception e) {
			// TODO: handle exception
			return new Response().Fail(Code.UPDATE_FAIL, Code.ADD_FAIL.getMsg());
		}
	}
	@Override
	public Response queryButtonsPower(Integer roleId) {
		// TODO Auto-generated method stub
		List<MenuBtnModel> resourceButtonsList = buttonMapper.queryButtonsPower(roleId);
		Map<String,List<MenuBtnModel>> map = new HashMap<String, List<MenuBtnModel>>();
		resourceButtonsList.forEach(xx ->{
			List<MenuBtnModel> list = null;
			String key = xx.getMenuId().toString();
			if(map.containsKey(key)) {
				list = map.get(key);
				list.add(xx);
				map.put(key, list);
			}else {
				list = new ArrayList<MenuBtnModel>();
				list.add(xx);
				map.put(key, list);
			}
		});
		return new Response().Success(Code.QUERY_SUCCESS,Code.QUERY_SUCCESS.getMsg()).addData("data", map);
	}
	
}
