package com.kiwihouse.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kiwihouse.controller.equipment.EquipmentController;
import com.kiwihouse.domain.vo.Response;
import com.kiwihouse.dto.ThresholdDto;
import com.kiwihouse.service.CheckAdminService;
import com.kiwihouse.service.FireThresholdService;
import com.kiwihouse.service.PrivilegeService;
import com.kiwihouse.vo.entire.Log;
import com.kiwihouse.vo.kiwihouse.ThresholdUpdateVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @author yjzn
 * @date 2020-03-17-下午 5:24
 */
@RestController
@RequestMapping("/threshold")
@Api(tags = "火警设备预警阀值")
public class FireThresholdController {
    private final Logger logger = LoggerFactory.getLogger(EquipmentController.class);

    @Autowired
    CheckAdminService checkAdminService;
    @Autowired
    FireThresholdService fireThresholdService;
    @Autowired
    PrivilegeService privilegeService;

    @ApiOperation(value = "updateInfo",
            notes = "<br>@description: <b>修改预警阀值</b></br>" +
                    "<br>@Date: <b>2020-3-17 17:36:37</b></br>",
            httpMethod = "PUT")
    @ApiResponses(@ApiResponse(code = 0,message ="回调参数：只有code和msg,无具体数据result"))
    @PutMapping("/info")
    public Response updateInfo(@RequestBody @Validated ThresholdUpdateVo updateVo, HttpServletRequest request){
        logger.info("修改预警阀值>> {}",new Log().setIp(request.getRemoteAddr()).setParam(updateVo.toString()));
        return fireThresholdService.updateInfo(updateVo);
    }

    @ApiOperation(value = "queryInfo",
            notes = "<br>@description: <b>查询预警阀值</b></br>" +
                    "<br>@Date: <b>2020-3-18 16:54:02</b></br>",
            httpMethod = "GET")
    @ApiResponses(@ApiResponse(code = 0,message ="回调参数",response = ThresholdDto.class))
    @GetMapping("/info")
    public Response queryInfo(@RequestParam(required = false) String imei, HttpServletRequest request){
        logger.info("查询预警阀值>> {}",new Log().setIp(request.getRemoteAddr()).setParam(imei));
        return fireThresholdService.queryInfo(imei);
    }
}
