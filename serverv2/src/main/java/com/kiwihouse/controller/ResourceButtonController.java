package com.kiwihouse.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kiwihouse.domain.vo.Response;
import com.kiwihouse.service.ResourceButtonService;
import com.kiwihouse.vo.entire.Log;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "静态页面按钮列表")
@RestController
@RequestMapping("/button")
public class ResourceButtonController {
	private static final Logger logger = LoggerFactory.getLogger(ResourceButtonController.class);

	@Autowired
	ResourceButtonService resourceButtonService;
	
	   @ApiOperation(value = "buttonListById",
	            notes = "<br>@description: <b>根据页面ID查询按钮列表</b></br>" +
	                    "<br>@Date: <b>2020-1-4 17:15:40</b></br>",
	            httpMethod = "GET")
	    @ApiResponses(@ApiResponse(code = 0,message ="回调参数：只有code和msg,无具体数据result"))
	    @GetMapping("/info/{resourceId}")
	    public Response buttonListById(@PathVariable Integer resourceId,HttpServletRequest request){
	        logger.info("告警转工单>> {}",new Log().setIp(request.getRemoteAddr()).setParam(resourceId));
	        Response resultList = resourceButtonService.buttonListById(resourceId);
	        logger.info("返回参数<< {}",resultList);
	        return resultList;
	    }
}
