package com.kiwihouse.controller.equipment;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.kiwihouse.dao.entity.vx.dev.Add;
import com.kiwihouse.dao.entity.vx.dev.WxEquipment;
import com.kiwihouse.domain.vo.Response;
import com.kiwihouse.dto.EqptInfoDto;
import com.kiwihouse.service.WxEquipmentService;
import com.kiwihouse.vo.entire.Log;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * 微信设备信息接口
 *
 * @author yjzn
 * @date 2019-12-30 10:26:27
 */
@RestController
@RequestMapping("/vx/equipment")
@Api(tags = "微信设备信息接口")
public class WxEqmentController {
	 private final Logger logger = LoggerFactory.getLogger(WxEqmentController.class);
	@Autowired
    WxEquipmentService equipmentService;
	
	@ApiOperation(value = "queryInfo",
            notes = "<br>@description: <b>查询用户设备信息</b></br>" +
                    "<br>@Return: <b>以区为单位进行区分</b></br>" +
                    "<br>@Date: <b>2019-12-30 10:33:36</b></br>",
            httpMethod = "GET")
    @ApiResponses({@ApiResponse(code = 0, message = "回调参数", response = EqptInfoDto.class)})
    @GetMapping("/info")
    public Map<String, Object> queryInfo(@Validated WxEquipment wxEquipment, HttpServletRequest request) {
		logger.info("查询用户设备信息>> {}",new Log().setIp(request.getRemoteAddr()).setParam(wxEquipment));
        return equipmentService.queryInfo(wxEquipment);
    }
	@ApiOperation(value = "updateInfo",
            notes = "<br>@description: <b>修改设备信息</b></br>" +
                    "<br>@Date: <b>2020-1-3 17:25:42</b></br>",
            httpMethod = "PUT")
    @ApiResponses(@ApiResponse(code = 0, message = "回调参数：只有code和msg,无具体数据result"))
    @PutMapping("/info")
    public Response updateInfo(@RequestBody @Validated Add wxEquipment, HttpServletRequest request) {
        logger.info("更新设备信息>> {}", new Log().setIp(request.getRemoteAddr()).setMsg("更新设备信息").setParam(wxEquipment.toString()));
        return equipmentService.updateInfo(wxEquipment);
    }

    @ApiOperation(value = "addInfo",
            notes = "<br>@description: <b>添加设备信息</b></br>" +
                    "<br>@Date: <b>2020-1-3 17:25:42</b></br>",
            httpMethod = "POST")
    @ApiResponses(@ApiResponse(code = 0, message = "回调参数：只有code和msg,无具体数据result"))
    @PostMapping("/info")
    public Response addInfo(@RequestBody @Validated Add wxEquipment, HttpServletRequest request) {
        logger.info("录入设备信息>> {}", new Log().setIp(request.getRemoteAddr()).setMsg("录入设备信息").setParam(wxEquipment.toString()));
        return equipmentService.addInfo(wxEquipment);
    }

    @ApiOperation(value = "deleteInfo",
            notes = "<br>@description: <b>删除设备信息</b></br>" +
                    "<br>@Date: <b>2020-1-3 17:25:42</b></br>",
            httpMethod = "DELETE")
    @ApiResponses(@ApiResponse(code = 0, message = "回调参数：只有code和msg,无具体数据result"))
    @ApiImplicitParam(paramType = "path", name = "eqptSn", dataType = "String", required = true, value = "设备序列号")
    @DeleteMapping("/info/{userId}/{imeis}")
    public Response deleteInfo(@PathVariable Integer userId,@PathVariable String imeis, HttpServletRequest request) {
        logger.info("删除设备信息>> {}", new Log().setIp(request.getRemoteAddr()).setMsg("删除设备信息").setParam(imeis));
        return equipmentService.deleteInfo(imeis, userId);
    }
    
    @ApiOperation(value = "queryInfo",
            notes = "<br>@description: <b>查询单个设备/b></br>" +
                    "<br>@Return: <b>以区为单位进行区分</b></br>" +
                    "<br>@Date: <b>2019-12-30 10:33:36</b></br>",
            httpMethod = "GET")
    @ApiResponses({@ApiResponse(code = 0, message = "回调参数", response = EqptInfoDto.class)})
    @GetMapping("/queryOneDev")
    public Response queryOneDev(@Validated WxEquipment wxEquipment, HttpServletRequest request) {
		logger.info("查询用户设备信息>> {}",new Log().setIp(request.getRemoteAddr()).setParam(wxEquipment));
        return equipmentService.queryOneDev(wxEquipment);
    }
}
