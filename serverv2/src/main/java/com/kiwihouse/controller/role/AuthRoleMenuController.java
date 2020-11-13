package com.kiwihouse.controller.role;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kiwihouse.common.bean.Code;
import com.kiwihouse.controller.common.BaseController;
import com.kiwihouse.dao.entity.AuthRoleMenu;
import com.kiwihouse.domain.vo.AuthRoleMenuDetails;
import com.kiwihouse.domain.vo.Response;
import com.kiwihouse.service.AuthRoleMenuService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RequestMapping("/rolemenu")
@RestController
@Api(tags = "角色菜单管理")
public class AuthRoleMenuController extends BaseController{
	@Autowired
    private AuthRoleMenuService authRoleMenuService;
	
	@ApiOperation(value = "获取用户对应角色的菜单信息以及用户没有的菜单信息", notes = "菜单权限操作", httpMethod = "GET")
    @GetMapping("menu")
    public Map<String, Object> list(Integer roleId,Integer curRoleId,
    		  Integer page,
    		  Integer limit,AuthRoleMenuDetails authRoleMenuDetails,Integer trigger) {
    	try {
    		if(authRoleMenuDetails==null) {
    			authRoleMenuDetails = new AuthRoleMenuDetails();
    		}
    		map = authRoleMenuService.selectPage(page,limit,roleId,curRoleId,authRoleMenuDetails,trigger);
    		map.put("code", 0);
    		map.put("msg",Code.QUERY_SUCCESS);
		} catch (Exception e) {
			// TODO: handle exception
			return putMsgToJsonString(0, Code.QUERY_FAIL.getMsg(), 0, null);
		}
        return map;
    }
	
	@ApiOperation(value = "添加角色菜单", httpMethod = "POST")
    @PostMapping("")
    public Response addRoleMenu(@RequestBody AuthRoleMenu authRoleMenu ) {
        boolean flag = authRoleMenuService.insertBatch(authRoleMenu.getRoleId(),authRoleMenu.getIds());
        if (flag) {
            return new Response().Success(Code.ADD_SUCCESS, Code.ADD_SUCCESS.getMsg());
        } else {
            return new Response().Fail(Code.ADD_FAIL, Code.ADD_FAIL.getMsg());
        }
    }
	
	@ApiOperation(value = "修改角色菜单权限", httpMethod = "PUT")
    @PutMapping("")
    public Response updRoleMenu(@RequestBody AuthRoleMenu authRoleMenu ) {
        boolean flag = authRoleMenuService.update(authRoleMenu);
        if (flag) {
            return new Response().Success(Code.UPDATE_SUCCESS, Code.UPDATE_SUCCESS.getMsg());
        } else {
            return new Response().Fail(Code.UPDATE_FAIL, Code.UPDATE_FAIL.getMsg());
        }
    }
	
	@ApiOperation(value = "根据角色ID删除角色", httpMethod = "DELETE")
    @DeleteMapping("{roleIds}/{menuIds}")
    public Response deleteRoleMenuByRoleMenuId(@PathVariable String roleIds,@PathVariable String menuIds) {
		
        boolean flag = authRoleMenuService.deleteRoleMenuByRoleMenuId(roleIds,menuIds);
        if (flag) {
            return new Response().Success(Code.DELETE_SUCCESS, Code.DELETE_SUCCESS.getMsg());
        } else {
            return new Response().Fail(Code.DELETE_FAIL, Code.DELETE_FAIL.getMsg());
        }
    }
}
