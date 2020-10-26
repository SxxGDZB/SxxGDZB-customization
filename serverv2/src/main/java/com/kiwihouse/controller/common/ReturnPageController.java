package com.kiwihouse.controller.common;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.kiwihouse.controller.equipment.EquipmentController;
import com.kiwihouse.dao.mapper.AuthUserMapper;
import com.kiwihouse.vo.entire.Log;

import io.swagger.annotations.Api;
@Api(tags = "页面跳转接口")
@RestController
@RequestMapping("/admin")
public class ReturnPageController extends BaseController{
	@Autowired
	AuthUserMapper authUserMapper;
	private final Logger logger = LoggerFactory.getLogger(ReturnPageController.class);
	
	@RequestMapping(value = "/{name}")
	public ModelAndView returnPage(@PathVariable String name,HttpServletRequest request) {
		logger.info("静态页面跳转>> {}", new Log().setIp(request.getRemoteAddr()).setParam(name));
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
