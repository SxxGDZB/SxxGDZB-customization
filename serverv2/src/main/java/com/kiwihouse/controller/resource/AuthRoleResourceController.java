package com.kiwihouse.controller.resource;

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
import com.kiwihouse.domain.vo.AuthRoleResourceVo;
import com.kiwihouse.domain.vo.Response;
import com.kiwihouse.service.AuthRoleResourceService;
import com.kiwihouse.shiro.filter.FilterChainManager;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/resourcerole")
@Api(tags = "角色资源管理")
public class AuthRoleResourceController extends BaseController{

	@Autowired
	AuthRoleResourceService authRoleResourceService;
	@Autowired
    private FilterChainManager filterChainManager;
	@ApiOperation(value = "获取角色权限对应列表", httpMethod = "GET")
    @GetMapping("list")
    public Map<String, Object> getRoleResourceList(Integer page, Integer limit,Integer roleId,AuthRoleResourceVo auResourceVo) {
        try {
    		if(auResourceVo==null) {
    			auResourceVo = new AuthRoleResourceVo();
    		}
    		System.out.println("------------------->" + auResourceVo.getTrigger() + "------------------->" +auResourceVo.getType());
    		map = authRoleResourceService.getRoleResourceList(page,limit,roleId,auResourceVo);
    		map.put("code", 0);
    		map.put("msg",Code.QUERY_SUCCESS);
		} catch (Exception e) {
			// TODO: handle exception
			return putMsgToJsonString(0, Code.QUERY_FAIL.getMsg(), 0, null);
		}
        return map;
    }
	@ApiOperation(value = "增加角色资源",httpMethod = "POST")
    @PostMapping("")
    public Response addAuthRoleResource(@RequestBody AuthRoleResourceVo auResourceVo) {

        Boolean flag = authRoleResourceService.addAuthRoleResource(auResourceVo);
        if (flag) {
        	    filterChainManager.reloadFilterChain();
            return new Response().Success(Code.ADD_SUCCESS,Code.ADD_SUCCESS.getMsg());
        } else {
            return new Response().Fail(Code.ADD_FAIL,Code.ADD_FAIL.getMsg());
        }
    }
	
	@ApiOperation(value = "删除角色资源", notes = "根据资源ID、角色ID 删除角色资源", httpMethod = "DELETE")
    @DeleteMapping("{ids}")
    public Response deleteMenuByMenuId(@PathVariable String ids) {

        Boolean flag = authRoleResourceService.deleteBatch(ids);
        if (flag) {
        	filterChainManager.reloadFilterChain();
            return new Response().Success(Code.DELETE_SUCCESS, Code.DELETE_SUCCESS.getMsg());
        } else {
            return new Response().Fail(Code.DELETE_FAIL, Code.DELETE_FAIL.getMsg());
        }
    }
	
	@ApiOperation(value = "修改角色资源", notes = "根据资源ID、角色ID 修改角色资源", httpMethod = "PUT")
	@PutMapping("{roleId}/{resIds}")
    public Response updAuthRoleResource(@PathVariable Integer roleId,@PathVariable String resIds) {
        Boolean flag = authRoleResourceService.updAuthRoleResource(roleId,resIds);
        if (flag) {
        	filterChainManager.reloadFilterChain();
            return new Response().Success(Code.UPDATE_SUCCESS, Code.UPDATE_SUCCESS.getMsg());
        } else {
            return new Response().Fail(Code.UPDATE_FAIL, Code.UPDATE_FAIL.getMsg());
        }
    }
	

}
