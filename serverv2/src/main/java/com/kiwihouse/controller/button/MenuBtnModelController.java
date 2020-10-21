package com.kiwihouse.controller.button;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import com.kiwihouse.dto.GroupDto;
import com.kiwihouse.service.MenuBtnService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * 	页面按钮模板操作
 * @author cmx
 *
 */
@Api(tags = "页面按钮模板操作")
@RestController
@RequestMapping("/button/model")
public class MenuBtnModelController extends BaseController{

	@Autowired
	MenuBtnService menuBtnService;
	
	 @ApiOperation(value = "info",
	            notes = "<br>@description: <b>查询页面按钮模板列表</b></br>" +
	                    "<br>@Date: <b>2020-3-5 15:45:51</b></br>",
	            httpMethod = "GET")
	 @ApiResponses(@ApiResponse(code = 0,message ="回调参数",response = GroupDto.class))
	 @GetMapping("/info")
	 public Map<String, Object> queryInfo(Integer page,Integer limit,Integer roleId){
	 	try {
	 		map = menuBtnService.queryInfo(page,limit,roleId);
	 		map.put("code", 0);
	 		map.put("msg",Code.QUERY_SUCCESS);
			} catch (Exception e) {
				// TODO: handle exception
				return putMsgToJsonString(0, Code.QUERY_FAIL.getMsg(), 0, null);
			}
	 	return map;
	 }
	 
	 @ApiOperation(value = "info",
	           notes = "<br>@description: <b>添加模板按钮</b></br>" +
	                   "<br>@Date: <b>2020-3-5 16:39:06</b></br>",
	           httpMethod = "POST")
		@ApiResponses(@ApiResponse(code = 0,message ="回调参数：只有code和msg,无具体数据result"))
		@PostMapping("/add")
		public Response addModelBtn(@RequestBody List<MenuBtnModel> list){
		    return menuBtnService.addModelBtn(list);
		}
	 @ApiOperation(value = "info",
	           notes = "<br>@description: <b>添加或修改模板按钮</b></br>" +
	                   "<br>@Date: <b>2020-3-5 16:39:06</b></br>",
	           httpMethod = "POST")
		@ApiResponses(@ApiResponse(code = 0,message ="回调参数：只有code和msg,无具体数据result"))
		@PostMapping("/addOrUpd")
		public Response addOrUpdModelBtn(@RequestBody List<MenuBtnModel> list){
		    return menuBtnService.addOrUpdModelBtn(list);
		}
	 
	 @ApiOperation(value = "info",
	           notes = "<br>@description: <b>删除模板一个菜单所有按钮</b></br>" +
	                   "<br>@Date: <b>2020-3-5 16:39:06</b></br>",
	           httpMethod = "POST")
		@ApiResponses(@ApiResponse(code = 0,message ="回调参数：只有code和msg,无具体数据result"))
		@DeleteMapping("/{menuId}")
		public Response deleteModelBtn(@PathVariable Integer menuId){
		    return menuBtnService.deleteModelBtn(menuId);
		}
}
