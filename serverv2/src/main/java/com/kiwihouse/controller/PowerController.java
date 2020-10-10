package com.kiwihouse.controller;
import java.text.ParseException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.kiwihouse.domain.vo.Response;
import com.kiwihouse.dto.ReportedDto;
import com.kiwihouse.service.PowerService;
import com.kiwihouse.vo.kiwihouse.ReportedQueryVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "功率追踪")
@RestController
@RequestMapping("/power")
public class PowerController {
	@Autowired
	PowerService powerService;
	@ApiOperation(value = "queryPwr",
            notes = "<br>@description: <b>查询设备上报功率数据</b></br>" +
                    "<br>@Date: <b>2020-3-30 13:32:52</b></br>",
            httpMethod = "GET")
    @ApiResponses({@ApiResponse(code = 0, message = "返回参数", response = ReportedDto.class)})
    @GetMapping(value = "/info")
    public Response queryPwr(@Validated ReportedQueryVo reportedQueryVo, HttpServletRequest request) throws ParseException {
         return powerService.queryPwr(reportedQueryVo);
    }
}
