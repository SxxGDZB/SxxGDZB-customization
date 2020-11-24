package com.kiwihouse.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kiwihouse.common.bean.Code;
import com.kiwihouse.dao.entity.SysDictionary;
import com.kiwihouse.domain.vo.Response;
import com.kiwihouse.service.SysDictionaryService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "字典表接口")
@RestController
@RequestMapping("/dictionary")
public class SysDictionaryController {

	@Autowired
	SysDictionaryService sysDictionaryService;
	
	@ApiOperation(value = "selectByType",
            notes = "<br>@description: 根据类型查找字典<b></b></br>" +
                    "<br>@Date: <b>2020-3-10 14:40:56</b></br>",
            httpMethod = "GET")
    @ApiResponses(@ApiResponse(code = 0,message ="回调参数：只有code和msg,无具体数据result"))
    @GetMapping("/selectByType/{type}")
	public Response selectByType(@PathVariable(required = true) String type) {
		try {
			List<Map> list = sysDictionaryService.selectByType(type);
			if(list.isEmpty()) {
				return new Response().Success(Code.QUERY_NULL,Code.QUERY_NULL.getMsg());
			}
			return new Response().Success(Code.QUERY_SUCCESS,Code.QUERY_SUCCESS.getMsg()).addData("data", list);
			
		}catch (Exception e) {
			// TODO: handle exception
			return new Response().Fail(Code.QUERY_FAIL,Code.QUERY_FAIL.getMsg());
		}
	}
	
	@ApiOperation(value = "selectByType",
            notes = "<br>@description: 根据类型和Key查找字典<b></b></br>" +
                    "<br>@Date: <b>2020-3-10 14:40:56</b></br>",
            httpMethod = "GET")
    @ApiResponses(@ApiResponse(code = 0,message ="回调参数：只有code和msg,无具体数据result"))
    @GetMapping("/selectByTypeAndKey/{type}/{key}")
	public Response selectByTypeAndKey(@PathVariable(required = true) String type,@PathVariable(required = true) String key) {
		try {
			SysDictionary sysDictionary = sysDictionaryService.selectByTypeAndKey(type,key);
			if(sysDictionary == null) {
				return new Response().Success(Code.QUERY_NULL,Code.QUERY_NULL.getMsg());
			}
			return new Response().Success(Code.QUERY_SUCCESS,Code.QUERY_SUCCESS.getMsg()).addData("data", sysDictionary);
			
		}catch (Exception e) {
			// TODO: handle exception
			return new Response().Fail(Code.QUERY_FAIL,Code.QUERY_FAIL.getMsg());
		}
	}
}
