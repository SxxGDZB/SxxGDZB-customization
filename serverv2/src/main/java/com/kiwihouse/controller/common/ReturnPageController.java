package com.kiwihouse.controller.common;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kiwihouse.dao.entity.AuthUser;
import com.kiwihouse.dao.mapper.AuthUserMapper;

import io.swagger.annotations.Api;
@Api(tags = "页面跳转接口")
@RestController
@RequestMapping("/admin")
public class ReturnPageController extends BaseController{
	@Autowired
	AuthUserMapper authUserMapper;
	
	private static String loginPage = "login";
	
	@RequestMapping(value = "/{name}")
	public ModelAndView returnPage(@PathVariable String name) {
		return new ModelAndView(name);
	}
	
	@RequestMapping(value = "/{name}/{url}")
	public ModelAndView returnPageTwo(@PathVariable String name,@PathVariable String url) {
		return new ModelAndView(name + "/" + url);
		
	}
	@RequestMapping(value = "/{name}/{url}/{path}")
	public ModelAndView returnPage(@PathVariable String name,@PathVariable String url,@PathVariable String path) {
		return new ModelAndView(name + "/" + url + "/" + path);
		
	}
	@RequestMapping(value = "/{name}/{url}/{path}/{path2}")
	public ModelAndView returnPage(@PathVariable String name,@PathVariable String url,@PathVariable String path,@PathVariable String path2) {
		return new ModelAndView(name + "/" + url + "/" + path + "/" +path2);
		
	}
}
