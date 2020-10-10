package com.kiwihouse.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kiwihouse.common.bean.Code;
import com.kiwihouse.dao.entity.ResourceButtons;
import com.kiwihouse.dao.mapper.ResourceButtonMapper;
import com.kiwihouse.domain.vo.Response;
import com.kiwihouse.service.ResourceButtonService;

@Service
public class ResourceButtonServiceImpl implements ResourceButtonService{
	
	@Autowired
	ResourceButtonMapper resourceButtonMapper;
	
	@Override
	public Response buttonListById(Integer resourceId) {
		// TODO Auto-generated method stub
		List<ResourceButtons> listResource = resourceButtonMapper.buttonListById(resourceId);
		return new Response().Success(Code.QUERY_SUCCESS,Code.QUERY_SUCCESS.getMsg()).addData("data", listResource);
	}
	
}
