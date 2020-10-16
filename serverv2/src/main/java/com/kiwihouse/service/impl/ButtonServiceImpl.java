package com.kiwihouse.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kiwihouse.common.bean.Code;
import com.kiwihouse.dao.entity.Buttons;
import com.kiwihouse.dao.mapper.ButtonMapper;
import com.kiwihouse.domain.vo.Response;
import com.kiwihouse.service.ButtonService;

@Service
public class ButtonServiceImpl implements ButtonService{

	@Autowired
	ButtonMapper buttonMapper;
	@Override
	public Map<String, Object> queryInfo(Integer page, Integer limit) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<Buttons> resourceButtonsList = null;
			if(limit != null) {
				resourceButtonsList =  buttonMapper.queryInfo((page - 1) * limit,  limit);
			}else {
				resourceButtonsList =  buttonMapper.queryInfo(null,  null);
			}
			
			Integer count = buttonMapper.queryCount();
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
	public Response queryButtonsPower() {
		// TODO Auto-generated method stub
		List<Buttons> resourceButtonsList = buttonMapper.queryButtonsPower();
		Map<String,List<Buttons>> map = new HashMap<String, List<Buttons>>();
		resourceButtonsList.forEach(xx ->{
			List<Buttons> list =null;
			if(map.containsKey(xx.getName())) {
				list = map.get(xx.getName());
				list.add(xx);
				map.put(xx.getName(), list);
			}else {
				list = new ArrayList<Buttons>();
				list.add(xx);
				map.put(xx.getName(), list);
			}
		});
		return new Response().Success(Code.QUERY_SUCCESS,Code.QUERY_SUCCESS.getMsg()).addData("data", map);
	}

}
