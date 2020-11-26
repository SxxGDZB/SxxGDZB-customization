package com.kiwihouse.controller.button;

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
import org.springframework.web.bind.annotation.RestController;

import com.kiwihouse.common.bean.Code;
import com.kiwihouse.controller.common.BaseController;
import com.kiwihouse.dao.entity.MenuBtnModel;
import com.kiwihouse.domain.vo.Response;
import com.kiwihouse.service.ButtonService;
import com.kiwihouse.service.MenuBtnService;
import com.kiwihouse.vo.entire.Log;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * 	静态资源按钮
 * @author cmx
 *
 */
@Api(tags = "静态资源按钮操作")
@RestController
@RequestMapping("/res/button")
public class MenuBtnController extends BaseController{
	private static final Logger logger = LoggerFactory.getLogger(MenuBtnController.class);
	
	@Autowired
	ButtonService buttonService;
	@Autowired
	MenuBtnService menuBtnService;
	
	@ApiOperation(value = "info",
	            notes = "<br>@description: <b>根据角色ID查询所有按钮权限</b></br>" +
	                    "<br>@Date: <b>2020-3-5 16:39:06</b></br>",
	            httpMethod = "GET")
   @ApiResponses(@ApiResponse(code = 0,message ="回调参数：只有code和msg,无具体数据result"))
   @GetMapping("/all/power/{roleId}")
   public Response queryButtonsPower(@PathVariable Integer roleId,HttpServletRequest request){
		logger.info("根据角色ID查询所有按钮权限>> {}",new Log().setIp(request.getRemoteAddr()).setParam(roleId));
		Response res = null;
		try {
			res = buttonService.queryButtonsPower(roleId);
			logger.info("根据角色ID查询所有按钮权限>> {查询成功}");
		} catch (Exception e) {
			// TODO: handle exception
			res = new Response().Fail(Code.QUERY_FAIL,Code.QUERY_FAIL.getMsg());
			logger.info("根据角色ID查询所有按钮权限>> {查询失败}");
		}
        return res;
   }
	
	@ApiOperation(value = "power",
           notes = "<br>@description: <b>添加或修改按钮权限</b></br>" +
                   "<br>@Date: <b>2020-3-5 16:39:06</b></br>",
           httpMethod = "POST")
	@ApiResponses(@ApiResponse(code = 0,message ="回调参数：只有code和msg,无具体数据result"))
	@PostMapping("/power")
	public Response insertOrUpdate(@RequestBody List<MenuBtnModel> list,HttpServletRequest request){
		String roleId = request.getHeader("dz-roleId");
		logger.info("添加或修改按钮权限>> {}",new Log().setIp(request.getRemoteAddr()).setParam(roleId).setParam(list));
		Response res = null;
		try {
			res = menuBtnService.insertOrUpdateBatch(list,Integer.valueOf(roleId));
			logger.info("添加或修改按钮权限>> {执行成功}");
		} catch (Exception e) {
			// TODO: handle exception
			res = new Response().Fail(Code.ADD_FAIL, Code.ADD_FAIL.getMsg());
			logger.info("添加或修改按钮权限>> {执行失败}");
		}
	    return res;
	}
}
