package com.kiwihouse.controller.menu;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kiwihouse.common.bean.Code;
import com.kiwihouse.dao.entity.SysMenu;
import com.kiwihouse.dao.mapper.SysDictionaryMapper;
import com.kiwihouse.domain.vo.Response;
import com.kiwihouse.dto.ResMenu;
import com.kiwihouse.service.SysMenuService;
import com.kiwihouse.util.ResponseList;
import com.kiwihouse.util.TreeUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
@Api(tags = "菜单接口")
@RestController
@RequestMapping("/menu")
public class SysMenuController {

	@Autowired
	SysMenuService sysMenuService;
	@Autowired
	SysDictionaryMapper sysDictionaryMapper;
	
	@ApiOperation(value = "根据UID获取用户所属菜单显示在左侧导航栏", httpMethod = "POST",notes = "根据UID")
	@PostMapping("/authMenuList")
	@ResponseBody
	public Response getAuthMenuList(Integer roleId) {
        List<SysMenu> treeNodes = new ArrayList<>();
        //得到左菜单
        treeNodes = sysMenuService.getAuthMenuList(roleId,0);
        JSONObject jo = new JSONObject();
        JSONObject jo_1 = new JSONObject();
        //删除首页
        for(int i=0; i< treeNodes.size();i++) {
        	if(treeNodes.get(i).getOrderNum()<0) {
        		jo_1.put("title",treeNodes.get(i).getName());
        	    jo_1.put("url",treeNodes.get(i).getUrl());
        		treeNodes.remove(i);
        	}
        }
        //形成树结构
        List<SysMenu> menuTreeNodes = TreeUtil.buildTreeBy2Loop(treeNodes,0);
        jo.put("homeInfo", jo_1);
        jo.put("menuInfo", menuTreeNodes);
        
        String vaule = sysDictionaryMapper.selectByKey("LOGO");
        JSONObject str = JSON.parseObject(vaule);
        System.out.println(str);
        jo.put("logoInfo", str);
		return new Response().Success(6666,"return menu list success").addData("menuTree",jo);
	}
	
	@ApiOperation(value = "根据UID获取用户所属菜单 --->包括已删除的菜单", httpMethod = "POST",notes = "根据UID")
	@PostMapping("/authMenuLists")
	@ResponseBody
	public Response getAuthMenuLists(Integer roleId) {
		Map<String, Object> map = new HashMap<String, Object>();
        List<SysMenu> treeNodes = new ArrayList<>();
        
        //得到左菜单
        treeNodes = sysMenuService.getAuthMenuList(roleId,1);
        //treeNodes = TreeUtil.buildTreeBy2Loop(treeNodes,0);
        map.put("count",treeNodes.size());
        map.put("code",0);
        map.put("masg", "success");
        map.put("data", treeNodes);
		return new Response().Success(6666,"return menu list success").addData("menuTree",map);
	}
	
	@ApiOperation(value = "更新菜单", httpMethod = "PUT")
    @PutMapping("")
    public Response updateMenu(@RequestBody SysMenu sysMenu) {
		boolean flag = sysMenuService.updateMenu(sysMenu);
        if (flag) {
            return new Response().Success(Code.UPDATE_SUCCESS, Code.UPDATE_SUCCESS.getMsg());
        } else {
            return new Response().Fail(Code.UPDATE_FAIL, Code.UPDATE_FAIL.getMsg());
        }
    }
	@ApiOperation(value = "删除菜单", httpMethod = "DELETE")
	@DeleteMapping("{ids}")
    public Response deleteMenu(@PathVariable String ids) {
		String [] idsStrArr = ids.split("_");
		boolean flag = sysMenuService.deleteBatchMenuByIds(idsStrArr);
//		boolean flag = sysMenuService.updateBatchMenuByIds(idsStrArr,0);
        if (flag) {
            return new Response().Success(Code.DELETE_SUCCESS, Code.DELETE_SUCCESS.getMsg());
        } else {
            return new Response().Fail(Code.DELETE_FAIL, Code.DELETE_FAIL.getMsg());
        }
    }
	
	@ApiOperation(value = "添加菜单", httpMethod = "POST")
    @PostMapping("")
    public Response addRole(@RequestBody SysMenu sysMenu) {

        boolean flag = sysMenuService.insert(sysMenu);
        if (flag) {
            return new Response().Success(Code.ADD_SUCCESS,Code.ADD_SUCCESS.getMsg());
        } else {
            return new Response().Fail(Code.ADD_FAIL,Code.ADD_FAIL.getMsg());
        }
    }
	
//	@ApiOperation(value = "恢复菜单", httpMethod = "POST")
//    @PutMapping("/recover/{ids}")
//    public Response recover(@PathVariable String ids) {
//		String [] idsStrArr = ids.split("_");
//        boolean flag = sysMenuService.updateBatchMenuByIds(idsStrArr,1);
//        if (flag) {
//            return new Response().Success(Code.RECOVER_SUCCESS,Code.RECOVER_SUCCESS.getMsg());
//        } else {
//            return new Response().Fail(Code.RECOVER_FAIL,Code.RECOVER_FAIL.getMsg());
//        }
//    }
	
	@ApiOperation(value = "根据RoleID获取静态按钮页面", httpMethod = "POST",notes = "根据RoleID")
	@PostMapping("/authMenuButtonLists")
	@ResponseBody
	public Response authMenuButtonLists(Integer roleId) {
		return new Response().Success(6666,"return menu list success").addData("data",sysMenuService.getAuthMenuButtonLists(roleId));
	}
	
	@ApiOperation(value = "根据菜单URL查询", httpMethod = "POST",notes = "根据RoleID")
	@PostMapping("/queryOneMenuByUrl")
	@ResponseBody
	public Response queryOneMenuByUrl(String url) {
		return sysMenuService.queryOneMenuByUrl(url);
	}
	
	@ApiOperation(value = "查询菜单模块路径", httpMethod = "GET")
	@GetMapping("/selectMenuUrlAndName")
	@ResponseBody
	public ResponseList<ResMenu> selectMenuUrlAndName() {
		return sysMenuService.selectMenuUrlAndName();
	}
	
}
