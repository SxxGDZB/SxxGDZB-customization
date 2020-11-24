package com.kiwihouse.controller.menu;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kiwihouse.common.bean.Code;
import com.kiwihouse.controller.common.BaseController;
import com.kiwihouse.dao.entity.AuthResource;
import com.kiwihouse.dao.entity.MenuResModel;
import com.kiwihouse.service.MenuResModelService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "菜单资源模板接口")
@RestController
@RequestMapping("/menures")
public class MenuResModelController extends BaseController{

	@Autowired
	MenuResModelService menuResModelService;
	
	@ApiOperation(value = "查询权限", notes = "权限操作", httpMethod = "GET")
    @GetMapping("/info")
    public Map<String, Object> getInfo(Integer page, Integer limit,MenuResModel menuResModel) {
//    	try {
    		if(menuResModel==null) {
    			menuResModel = new MenuResModel();
    		}
    		map = menuResModelService.selectAllPage(page,limit,menuResModel);
    		map.put("code", 0);
    		map.put("msg",Code.QUERY_SUCCESS);
//		} catch (Exception e) {
//			// TODO: handle exception
//			return putMsgToJsonString(0, Code.QUERY_FAIL.getMsg(), 0, null);
//		}
        return map;
    }
}
