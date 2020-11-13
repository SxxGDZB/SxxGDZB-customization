package com.kiwihouse.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kiwihouse.common.bean.Code;
import com.kiwihouse.common.utils.RedisUtil;
import com.kiwihouse.dao.entity.Share;
import com.kiwihouse.dao.mapper.ShareMapper;
import com.kiwihouse.domain.vo.Response;
import com.kiwihouse.service.ShareService;

@Service
public class ShareServiceImpl implements ShareService{

	@Autowired
	ShareMapper shareMapper;
	@Autowired
	RedisUtil redisUtil;
	
	@Override
	public Response insert(Share share) {
		try {
			String key = "_" + share.getShareUserId() +  "_" + share.getImei() + "_" + share.getSharerId() ;
			
			Share s = shareMapper.queryShareDev(share);
			
			shareMapper.insert(share);
			if(redisUtil.hasKey(key)) {
				redisUtil.delete(key);
			}
			return new Response().Success(Code.ADD_SUCCESS,Code.ADD_SUCCESS.getMsg());
		} catch (Exception e) {
			// TODO: handle exception
			return new Response().Fail(Code.ADD_FAIL,Code.ADD_FAIL.getMsg());
		}
	}

	
}
