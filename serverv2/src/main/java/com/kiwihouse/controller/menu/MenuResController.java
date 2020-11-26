package com.kiwihouse.controller.menu;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kiwihouse.common.bean.Code;
import com.kiwihouse.dao.entity.MenuRes;
import com.kiwihouse.domain.vo.Response;
import com.kiwihouse.service.MenuResService;
import com.kiwihouse.vo.entire.Log;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "菜单资源接口")
@RestController
@RequestMapping("/menures")
public class MenuResController {
	private static final Logger logger = LoggerFactory.getLogger(MenuResController.class);
	@Autowired
	MenuResService menuResService;
	
	@ApiOperation(value = "根据RoleID获取静态页面以及对应的资源列表", httpMethod = "GET",notes = "根据RoleID")
	@GetMapping("/resPageByRoleId/{roleId}")
	@ResponseBody
	public Response resPageByRoleId(@PathVariable Integer roleId) {
		
		return menuResService.resPageByRoleId(roleId);
	}
	
	
	@ApiOperation(value = "power",
	           notes = "<br>@description: <b>修改菜单资源权限</b></br>" +
	                   "<br>@Date: <b>2020-3-5 16:39:06</b></br>",
	           httpMethod = "POST")
	@ApiResponses(@ApiResponse(code = 0,message ="回调参数：只有code和msg,无具体数据result"))
	@PostMapping("/power/{roleId}")
	public Response insertOrUpdate(@RequestBody List<MenuRes> list,@PathVariable Integer roleId,HttpServletRequest request){
		logger.info("添加或修改按钮权限>> {}",new Log().setIp(request.getRemoteAddr()).setParam(roleId).setParam(list));
		Response res = null;
		try {
			res = menuResService.menuResUpdBatch(list,roleId);
			logger.info("添加或修改按钮权限>> {执行成功}");
		} catch (Exception e) {
			// TODO: handle exception
			res = new Response().Fail(Code.ADD_FAIL, Code.ADD_FAIL.getMsg());
			logger.info("添加或修改按钮权限>> {执行失败}");
		}
	    return res;
	}
}
