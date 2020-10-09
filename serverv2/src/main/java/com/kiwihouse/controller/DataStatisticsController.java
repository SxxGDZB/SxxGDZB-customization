package com.kiwihouse.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kiwihouse.dto.Statistics;
import com.kiwihouse.service.DataStatisticsService;
import com.kiwihouse.service.PrivilegeService;
import com.kiwihouse.vo.entire.Log;
import com.kiwihouse.vo.entire.ResultList;
import com.kiwihouse.vo.kiwihouse.DataStatisticsVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @author yjzn
 * @date 2020-02-24-下午 2:00
 */
@Validated
@Api(tags = "数据总览接口")
@RestController
@RequestMapping("/statistics")
public class DataStatisticsController {
    private final Logger logger = LoggerFactory.getLogger(DataStatisticsController.class);

    @Autowired
    DataStatisticsService dataStatisticsService;
    @Autowired
    PrivilegeService privilegeService;

    @ApiOperation(value = "queryInfo",
            notes = "<br>@description: <b>数据总览所有人可查看</b></br>" +
                    "<br>@Date: <b>2020-2-24 15:41:26</b></br>",
            httpMethod = "GET")
    @ApiResponses({@ApiResponse(code = 0, message = "返回参数", response = Statistics.class)})
    @GetMapping("/info")
    public ResultList queryInfo(@Validated DataStatisticsVo dataStatisticsVo, HttpServletRequest request){
        logger.info("查询数据总览信息>> {}",new Log().setIp(request.getRemoteAddr()).setParam(dataStatisticsVo.toString()));
//        String adminId = request.getHeader("dz-usr");
//        if(StringUtils.isBlank(adminId)){
//            return ResultUtil.resp(Code.PRIVILEGE_FAIL);
//        }
        return dataStatisticsService.queryInfo(dataStatisticsVo);
    }
    @ApiOperation(value = "queryInfoByImei",
            notes = "<br>@description: <b>查询一个设备、一段时间的告警信息</b></br>" +
                    "<br>@Date: <b>2020-2-24 15:41:26</b></br>",
            httpMethod = "GET")
    @ApiResponses({@ApiResponse(code = 0, message = "返回参数", response = Statistics.class)})
    @GetMapping("/one/dev")
    public ResultList queryInfoByImei(@Validated DataStatisticsVo dataStatisticsVo, HttpServletRequest request){
        logger.info("查询一个设备、一段时间的告警信息>> {}",new Log().setIp(request.getRemoteAddr()).setParam(dataStatisticsVo.toString()));
        return dataStatisticsService.queryInfoByImei(dataStatisticsVo);
    }
}
