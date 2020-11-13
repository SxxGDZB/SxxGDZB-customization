package com.kiwihouse.controller;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kiwihouse.common.bean.Code;
import com.kiwihouse.common.bean.DataType;
import com.kiwihouse.common.utils.RedisUtil;
import com.kiwihouse.dao.entity.IMEI;
import com.kiwihouse.dao.entity.Share;
import com.kiwihouse.dao.mapper.WxEquipmentMapper;
import com.kiwihouse.domain.vo.Response;
import com.kiwihouse.service.ShareService;
import com.kiwihouse.service.WxEquipmentService;
import com.kiwihouse.vo.entire.Log;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "分享设备")
@RestController
@RequestMapping("/share")
public class ShareController {
	
	private final Logger logger = LoggerFactory.getLogger(ShareController.class);
	@Autowired
	RedisUtil redisUtil;
	@Autowired
	WxEquipmentMapper wxEquipmentMapper;
	@Autowired
	WxEquipmentService wxEquipmentService;
	@Autowired
	ShareService shareService;
	
	@ApiOperation(value = "dev",
            notes = "<br>@description: <b>分享设备</b></br>" +
                    "<br>@Date: <b>2020-1-4 17:15:40</b></br>",
            httpMethod = "POST")
    @ApiResponses(@ApiResponse(code = 0,message ="回调参数：只有code和msg,无具体数据result"))
    @PostMapping("/dev")
    public Response addInfo(@RequestBody Share share,HttpServletRequest request){
        logger.info("分享设备>> {}",new Log().setIp(request.getRemoteAddr()).setParam(share));
        /*分享给这些用户*/
        String [] userArr = share.getShareUserIds().split(",");
        for (int i = 0; i < userArr.length; i++) {
        	String key = "_" + userArr[i] +  "_" + share.getImei() + "_" + share.getSharerId() ;
        	String data = redisUtil.get(key);
        	if(data == null) {
        		System.out.println("=================>存储IMEI");
        		redisUtil.set(key ,share.getImei());
        		redisUtil.expire(key,DataType.shareTime,TimeUnit.MINUTES);
        	}else {
        		if(!data.contains(share.getImei())) {
        			System.out.println("=================>加入IMEI");
        			redisUtil.set(key ,data + "," + share.getImei());
        			redisUtil.expire(key,DataType.shareTime,TimeUnit.MINUTES);
        		}else {
        			redisUtil.expire(key,DataType.shareTime,TimeUnit.MINUTES);
        		}
        	}
		}
        return new Response().Success(Code.SHARE_SUCCESS,Code.SHARE_SUCCESS.getMsg());
    }
	
	@ApiOperation(value = "dev",
            notes = "<br>@description: <b>查询分享设备</b></br>" +
                    "<br>@Date: <b>2020-1-4 17:15:40</b></br>",
            httpMethod = "GET")
    @ApiResponses(@ApiResponse(code = 0,message ="回调参数：只有code和msg,无具体数据result"))
    @GetMapping("/dev/{userId}")
    public Map<String, Object> queryInfo(@PathVariable Integer userId,HttpServletRequest request){
        logger.info("查询分享设备>> {}",new Log().setIp(request.getRemoteAddr()).setParam(userId));
		Set<String> imeiArray = redisUtil.keys( "_" + userId + "_*");
		List<IMEI> listIMEI = new ArrayList<IMEI>();
		List<Integer> listUser = new ArrayList<Integer>();
        imeiArray.forEach(xx ->{
        	System.out.println(xx);
        	listIMEI.add(new IMEI(redisUtil.get(xx)));
        	listUser.add(Integer.valueOf(xx.split("_")[3]));
        });
        return wxEquipmentService.queryShareDevList(listIMEI,listUser);
    }
	
	@ApiOperation(value = "accept",
            notes = "<br>@description: <b>接受分享</b></br>" +
                    "<br>@Date: <b>2020-1-4 17:15:40</b></br>",
            httpMethod = "POST")
    @ApiResponses(@ApiResponse(code = 0,message ="回调参数：只有code和msg,无具体数据result"))
    @PostMapping("/accept")
    public Response acceptShare(@RequestBody Share share,HttpServletRequest request){
        logger.info("接受分享>> {}",new Log().setIp(request.getRemoteAddr()).setParam(share));
        return shareService.insert(share);
    }
	
	public static void main(String[] args) {
		String a = "_1002_2001_7897989456546";
		String [] str = a.split("_");
		System.out.println(str[2]);
	}
}
