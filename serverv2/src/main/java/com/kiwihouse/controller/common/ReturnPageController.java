package com.kiwihouse.controller.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.authc.AuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kiwihouse.common.bean.Code;
import com.kiwihouse.common.domain.Jwt;
import com.kiwihouse.dao.entity.AuthUser;
import com.kiwihouse.dao.entity.AuthUserRole;
import com.kiwihouse.dao.mapper.AuthUserMapper;
import com.kiwihouse.dao.mapper.AuthUserRoleMapper;
import com.kiwihouse.domain.vo.JwtAccount;
import com.kiwihouse.domain.vo.Response;
import com.kiwihouse.util.JsonWebTokenUtil;
import com.kiwihouse.util.spring.SpringUtils;
import com.kiwihouse.vo.entire.Log;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.swagger.annotations.Api;
@Api(tags = "页面跳转接口")
@RestController
@RequestMapping("/admin")
public class ReturnPageController extends BaseController{
	@Autowired
	AuthUserMapper authUserMapper;
	private final Logger logger = LoggerFactory.getLogger(ReturnPageController.class);
	
	@RequestMapping(value = "/{name}")
	public ModelAndView returnPage(@PathVariable String name,String jwt,HttpServletRequest request) {
		return new ModelAndView(name);
	}
	
	@RequestMapping(value = "/{name}/{url}")
	public ModelAndView returnPageTwo(@PathVariable String name,@PathVariable String url,HttpServletRequest request) {
		logger.info("静态页面跳转>> {}", new Log().setIp(request.getRemoteAddr()).setParam(name + "/" + url));
		return new ModelAndView(name + "/" + url);
	}
	@RequestMapping(value = "/{name}/{url}/{path}")
	public ModelAndView returnPage(@PathVariable String name,@PathVariable String url,@PathVariable String path,HttpServletRequest request) {
		logger.info("静态页面跳转>> {}", new Log().setIp(request.getRemoteAddr()).setParam(name + "/" + url + "/" + path));
		return new ModelAndView(name + "/" + url + "/" + path);
		
	}
	@RequestMapping(value = "/{name}/{url}/{path}/{path2}")
	public ModelAndView returnPage(@PathVariable String name,@PathVariable String url,@PathVariable String path,@PathVariable String path2,HttpServletRequest request) {
		logger.info("静态页面跳转>> {}", new Log().setIp(request.getRemoteAddr()).setParam(name + "/" + url + "/" + path + "/" +path2));
		return new ModelAndView(name + "/" + url + "/" + path + "/" +path2);
		
	}
	
}
