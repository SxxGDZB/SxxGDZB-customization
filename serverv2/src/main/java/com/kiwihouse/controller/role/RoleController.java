package com.kiwihouse.controller.role;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kiwihouse.common.bean.Code;
import com.kiwihouse.controller.common.BaseController;
import com.kiwihouse.dao.entity.AuthResource;
import com.kiwihouse.dao.entity.AuthRole;
import com.kiwihouse.dao.entity.AuthUser;
import com.kiwihouse.domain.vo.Response;
import com.kiwihouse.service.AuthRoleMenuService;
import com.kiwihouse.service.ResourceService;
import com.kiwihouse.service.RoleService;
import com.kiwihouse.service.UserService;
import com.kiwihouse.shiro.filter.FilterChainManager;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author tomsun28
 * @date 20:02 2018/3/20
 */
@RequestMapping("/role")
@RestController
@Api(tags = "角色管理")
public class RoleController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @Autowired
    private ResourceService resourceService;
    
    @Autowired
    private AuthRoleMenuService authRoleMenuService;

    @Autowired
    private FilterChainManager filterChainManager;

    @SuppressWarnings("unchecked")
    @ApiOperation(value = "获取角色关联的(roleId)对应用户列表",httpMethod = "GET")
    @GetMapping("user/{roleId}/{currentPage}/{pageSize}")
    public Response getUserListByRoleId(@PathVariable Integer roleId, @PathVariable Integer currentPage, @PathVariable Integer pageSize) {
        PageHelper.startPage(currentPage,pageSize);
        List<AuthUser> users = userService.getUserListByRoleId(roleId);
        users.forEach(user->user.setPassword(null));
        PageInfo pageInfo = new PageInfo(users);
        return new Response().Success(6666,"return users success").addData("data",pageInfo);
    }

    @SuppressWarnings("unchecked")
    @ApiOperation(value = "获取角色未关联的用户列表", httpMethod = "GET")
    @GetMapping("user/-/{roleId}/{currentPage}/{pageSize}")
    public Response getUserListExtendByRoleId(@PathVariable Integer roleId, @PathVariable Integer currentPage, @PathVariable Integer pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        List<AuthUser> users = userService.getNotAuthorityUserListByRoleId(roleId);
        users.forEach(user -> user.setPassword(null));
        PageInfo pageInfo = new PageInfo(users);
        return new Response().Success(6666, "return users success").addData("data", pageInfo);
    }


    @SuppressWarnings("unchecked")
    @ApiOperation(value = "获取角色(roleId)所被授权的API资源")
    @GetMapping("api/{roleId}/{currentPage}/{pageSize}")
    public Response getRestApiExtendByRoleId(@PathVariable Integer roleId, @PathVariable Integer currentPage, @PathVariable Integer pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        List<AuthResource> authResources = resourceService.getAuthorityApisByRoleId(roleId);
        PageInfo pageInfo = new PageInfo(authResources);
        return new Response().Success(6666, "return api success").addData("data", pageInfo);
    }

    @SuppressWarnings("unchecked")
    @ApiOperation(value = "获取角色(roleId)未被授权的API资源")
    @GetMapping("api/-/{roleId}/{currentPage}/{pageSize}")
    public Response getRestApiByRoleId(@PathVariable Integer roleId, @PathVariable Integer currentPage, @PathVariable Integer pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        List<AuthResource> authResources = resourceService.getNotAuthorityApisByRoleId(roleId);
        PageInfo pageInfo = new PageInfo(authResources);
        return new Response().Success(6666, "return api success").addData("data", pageInfo);
    }

    @SuppressWarnings("unchecked")
    @ApiOperation(value = "获取角色(roleId)所被授权的menu资源")
    @GetMapping("menu/{roleId}/{currentPage}/{pageSize}")
    public Response getMenusByRoleId(@PathVariable Integer roleId, @PathVariable Integer currentPage, @PathVariable Integer pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        List<AuthResource> authResources = resourceService.getAuthorityMenusByRoleId(roleId);
        PageInfo pageInfo = new PageInfo(authResources);
        return new Response().Success(6666, "return api success").addData("data", pageInfo);
    }

    @SuppressWarnings("unchecked")
    @ApiOperation(value = "获取角色(roleId)未被授权的menu资源")
    @GetMapping("menu/-/{roleId}/{currentPage}/{pageSize}")
    public Response getMenusExtendByRoleId(@PathVariable Integer roleId, @PathVariable Integer currentPage, @PathVariable Integer pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        List<AuthResource> authResources = resourceService.getNotAuthorityMenusByRoleId(roleId);
        PageInfo pageInfo = new PageInfo(authResources);
        return new Response().Success(6666, "return api success").addData("data", pageInfo);
    }

    @ApiOperation(value = "授权资源给角色",httpMethod = "POST")
    @PostMapping("/authority/resource")
    public Response authorityRoleResource(HttpServletRequest request) {
//        Map<String,String> map = getRequestBody(request);
//        int roleId = Integer.parseInt(map.get("roleId"));
//        int resourceId = Integer.parseInt(map.get("resourceId"));
//        boolean flag = roleService.authorityRoleResource(roleId,resourceId);
//        shiroFilterChainManager.reloadFilterChain();
//        return flag ? new ResponseMsg().Success(6666,"authority success") : new ResponseMsg().Fail(1111,"authority error");
        return new Response();
    }

    @ApiOperation(value = "删除对应的角色的授权资源",httpMethod = "DELETE")
    @DeleteMapping("/authority/resource/{roleId}/{resourceId}")
    public Response deleteAuthorityRoleResource(@PathVariable Integer roleId, @PathVariable Integer resourceId ) {
        boolean flag = roleService.deleteAuthorityRoleResource(roleId,resourceId);
        filterChainManager.reloadFilterChain();
        return flag ? new Response().Success(6666,"authority success") : new Response().Fail(1111,"authority error");
    }

    @SuppressWarnings("unchecked")
    @ApiOperation(value = "获取角色LIST", httpMethod = "GET")
    @GetMapping("{currentPage}/{pageSize}")
    public Response getRoles(@PathVariable Integer currentPage, @PathVariable Integer pageSize) {

        PageHelper.startPage(currentPage, pageSize);
        List<AuthRole> roles = roleService.getRoleList();
        PageInfo pageInfo = new PageInfo(roles);
        return new Response().Success(6666, "return roles success").addData("data", pageInfo);
    }

    @ApiOperation(value = "添加角色", httpMethod = "POST")
    @PostMapping("")
    public Response addRole(@RequestBody AuthRole role) {

        boolean flag = roleService.addRole(role);
        if (flag) {
            return new Response().Success(6666, "add role success");
        } else {
            return new Response().Fail(111, "add role fail");
        }
    }

    @ApiOperation(value = "更新角色", httpMethod = "PUT")
    @PutMapping("")
    public Response updateRole(@RequestBody AuthRole role) {

        boolean flag = roleService.updateRole(role);
        if (flag) {
            return new Response().Success(6666, "update success");
        } else {
            return new Response().Fail(1111, "update fail");
        }
    }

    @ApiOperation(value = "根据角色ID删除角色", httpMethod = "DELETE")
    @DeleteMapping("{roleId}")
    public Response deleteRoleByRoleId(@PathVariable Integer roleId) {

        boolean flag = roleService.deleteRoleByRoleId(roleId);
        if (flag) {
            return new Response().Success(6666, "delete success");
        } else {
            return new Response().Fail(1111, "delete fail");
        }
    }

    @ApiOperation(value = "获取用户对应所有角色信息",httpMethod = "POST")
    @PostMapping("user/{roleId}")
    public Response queryAuthRole(@PathVariable Integer roleId) {
    	List<AuthRole> list  = roleService.queryAuthRole(roleId);
		return new Response().Success(Code.QUERY_SUCCESS, Code.QUERY_SUCCESS.getMsg()).addData("list", list);
    }
    
	@ApiOperation(value = "角色列表", notes = "查询", httpMethod = "GET")
    @GetMapping("/all")
    public Map<String, Object> list(Integer page, Integer limit) {
//    	try {
    		
    		map = roleService.getSelectRolesList(page,limit);
    		map.put("code", 0);
    		map.put("msg",Code.QUERY_SUCCESS);
//		} catch (Exception e) {
			// TODO: handle exception
//			return putMsgToJsonString(0, Code.QUERY_FAIL.getMsg(), 0, null);
//		}
        return map;
    }
}
