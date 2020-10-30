package com.kiwihouse.controller.button;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kiwihouse.controller.common.BaseController;
import com.kiwihouse.dao.entity.MenuBtnModel;
import com.kiwihouse.domain.vo.Response;
import com.kiwihouse.service.ButtonService;
import com.kiwihouse.service.MenuBtnService;

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
	@Autowired
	 ButtonService buttonService;
	 @Autowired
	 MenuBtnService menuBtnService;
	 
	@ApiOperation(value = "info",
	            notes = "<br>@description: <b>查询所有按钮权限</b></br>" +
	                    "<br>@Date: <b>2020-3-5 16:39:06</b></br>",
	            httpMethod = "POST")
   @ApiResponses(@ApiResponse(code = 0,message ="回调参数：只有code和msg,无具体数据result"))
   @GetMapping("/all/power/{roleId}")
   public Response queryButtonsPower(@PathVariable Integer roleId){
       return buttonService.queryButtonsPower(roleId);
   }
	
	@ApiOperation(value = "info",
           notes = "<br>@description: <b>添加或修改按钮权限</b></br>" +
                   "<br>@Date: <b>2020-3-5 16:39:06</b></br>",
           httpMethod = "POST")
	@ApiResponses(@ApiResponse(code = 0,message ="回调参数：只有code和msg,无具体数据result"))
	@PostMapping("/power")
	public Response insertOrUpdate(@RequestBody List<MenuBtnModel> list,HttpServletRequest request){
		String roleId = request.getHeader("dz-roleId");
	    return menuBtnService.insertOrUpdateBatch(list,Integer.valueOf(roleId));
	}
}
