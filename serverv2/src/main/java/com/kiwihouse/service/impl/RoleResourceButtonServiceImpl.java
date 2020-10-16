package com.kiwihouse.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kiwihouse.common.bean.Code;
import com.kiwihouse.dao.entity.Buttons;
import com.kiwihouse.dao.mapper.RoleResourceButtonMapper;
import com.kiwihouse.domain.vo.Response;
import com.kiwihouse.service.RoleResourceButtonService;

@Service
public class RoleResourceButtonServiceImpl implements RoleResourceButtonService{
	
	@Autowired
	RoleResourceButtonMapper roleResourceButtonMapper;
	
	@Override
	public Response buttonListById(Integer resourceId,Integer roleId) {
		// TODO Auto-generated method stub
		List<Buttons> resourceButtonsList = roleResourceButtonMapper.buttonListById(resourceId,roleId);
		resourceButtonsList.forEach(xx ->{
			System.out.println(xx.toString());
		});
//		for(RoleResourceButton rrb : roleResourceButtonList) {
//			resourceButtonsList.add(rrb.getResourceButtons());
//		}
		return new Response().Success(Code.QUERY_SUCCESS,Code.QUERY_SUCCESS.getMsg()).addData("data", resourceButtonsList);
	}
	
}
