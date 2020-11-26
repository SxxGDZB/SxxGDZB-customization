package com.kiwihouse.controller.menu;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kiwihouse.common.bean.Code;
import com.kiwihouse.controller.common.BaseController;
import com.kiwihouse.dao.entity.MenuResModel;
import com.kiwihouse.domain.vo.Response;
import com.kiwihouse.service.MenuResModelService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "菜单资源模板接口")
@RestController
@RequestMapping("/menuresmodel")
public class MenuResModelController extends BaseController{

	@Autowired
	MenuResModelService menuResModelService;
	
	@ApiOperation(value = "菜单资源模板列表", notes = "菜单资源模板列表查询", httpMethod = "GET")
    @GetMapping("/info")
    public Map<String, Object> getInfo(Integer page, Integer limit,MenuResModel menuResModel) {
    	try {
    		if(menuResModel==null) {
    			menuResModel = new MenuResModel();
    		}
    		map = menuResModelService.selectAllPage(page,limit,menuResModel);
    		map.put("code", 0);
    		map.put("msg",Code.QUERY_SUCCESS);
		} catch (Exception e) {
			// TODO: handle exception
			return putMsgToJsonString(0, Code.QUERY_FAIL.getMsg(), 0, null);
		}
        return map;
    }
	
	@ApiOperation(value = "菜单资源模板不存在的资源列表", notes = "菜单资源模板不存在的资源列表查询", httpMethod = "GET")
    @GetMapping("/selectMenuNotResList")
    public Map<String, Object> selectMenuNotResList(Integer page, Integer limit,MenuResModel menuResModel) {
//    	try {
    		if(menuResModel==null) {
    			menuResModel = new MenuResModel();
    		}
    		map = menuResModelService.selectMenuNotResList(page,limit,menuResModel);
    		map.put("code", 0);
    		map.put("msg",Code.QUERY_SUCCESS);
//		} catch (Exception e) {
//			// TODO: handle exception
//			return putMsgToJsonString(0, Code.QUERY_FAIL.getMsg(), 0, null);
//		}
        return map;
    }
	
	
	@ApiOperation(value = "add", notes = "<br>@description: <b>添加菜单模板资源</b></br>"
			+ "<br>@Date: <b>2020-3-5 16:39:06</b></br>", httpMethod = "POST")
	@ApiResponses(@ApiResponse(code = 0, message = "回调参数：只有code和msg,无具体数据result"))
	@PostMapping("/add")
	public Response addMenuModelRes(@RequestBody List<MenuResModel> list) {
		return menuResModelService.insertBatchMenuResModel(list);
	}
	
	@ApiOperation(value = "", notes = "<br>@description: <b>删除模板一个菜单所有按钮</b></br>"
			+ "<br>@Date: <b>2020-3-5 16:39:06</b></br>", httpMethod = "DELETE")
	@ApiResponses(@ApiResponse(code = 0, message = "回调参数：只有code和msg,无具体数据result"))
	@DeleteMapping("/{ids}")
	public Response delMenuModelRes(@PathVariable String ids) {
		return menuResModelService.deleteBatchMenuResModel(ids);
	}
	
	@ApiOperation(value = "根据RoleID获取静态页面以及对应的资源列表模板", httpMethod = "GET",notes = "根据RoleID")
	@GetMapping("/resModelPageByRoleId/{roleId}")
	@ResponseBody
	public Response resPageByRoleId(@PathVariable Integer roleId) {
		
		return menuResModelService.resModelPageByRoleId(roleId);
	}
}
