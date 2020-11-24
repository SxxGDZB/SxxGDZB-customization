package com.kiwihouse.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kiwihouse.common.bean.Code;
import com.kiwihouse.controller.common.BaseController;
import com.kiwihouse.dao.entity.DevHistoryDate;
import com.kiwihouse.dao.entity.DevHistoryThree;
import com.kiwihouse.domain.vo.Response;
import com.kiwihouse.dto.ReportedDto;
import com.kiwihouse.service.DevHistoryDateService;
import com.kiwihouse.util.excel.ExcelUtil;
import com.kiwihouse.vo.entire.Log;
import com.kiwihouse.vo.kiwihouse.ReportedQueryVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/history")
@Api(tags = "历史记录")
public class DevHistoryDateController extends BaseController{
	private final Logger logger = LoggerFactory.getLogger(DevHistoryDateController.class);

	@Autowired
	DevHistoryDateService historyDateService;
	@ApiOperation(value = "queryInfo",
            notes = "<br>@description: <b>查询设备历史记录 ---》返回统计</b></br>" +
                    "<br>@Date: <b>2020-1-3 15:36:22</b></br>",
            httpMethod = "GET")
    @ApiResponses({@ApiResponse(code = 0, message = "返回参数", response = ReportedDto.class)})
    @GetMapping(value = "/info")
	public Response historyDevInfo(@Validated ReportedQueryVo reportedQueryVo) {
		
		return historyDateService.historyDevInfo(reportedQueryVo);
		
	}
	@ApiOperation(value = "selectAll",
            notes = "<br>@description: <b>查询设备历史记录</b></br>" +
                    "<br>@Date: <b>2020-1-3 15:36:22</b></br>",
            httpMethod = "GET")
    @ApiResponses({@ApiResponse(code = 0, message = "返回参数", response = ReportedDto.class)})
    @GetMapping(value = "/selectAll")
	public Map<String, Object> selectAll(@Validated ReportedQueryVo reportedQueryVo) {
		try {
			map = historyDateService.selectAll(reportedQueryVo);
			map.put("code", 0);
			map.put("msg",Code.QUERY_SUCCESS);
			return map;
		} catch (Exception e) {
			// TODO: handle exception
			return putMsgToJsonString(0, Code.QUERY_FAIL.getMsg(), 0, null);
		}
		
		
		
		
	}
	
	@ApiOperation(value = "export", notes = "<br>@description: <b>Excel实时数据导出</b></br>"
			+ "<br>@Date: <b>2020-1-4 17:15:40</b></br>", httpMethod = "POST")
	@ApiResponses(@ApiResponse(code = 0, message = "回调参数：只有code和msg,无具体数据result"))
	@PostMapping("/export")
	public Response export(@RequestBody ReportedQueryVo reportedQueryVo, HttpServletRequest request) {
		logger.info("Excel实时数据导出>> {}", new Log().setIp(request.getRemoteAddr()).setParam(reportedQueryVo));
		reportedQueryVo.setLimit(0);
		Map<String, Object> map = historyDateService.selectAll(reportedQueryVo);
		if("0".equals(reportedQueryVo.getEqptType())) {
			List<DevHistoryDate> list = (List<DevHistoryDate>) map.get("data");
			ExcelUtil<DevHistoryDate> util = new ExcelUtil<DevHistoryDate>(DevHistoryDate.class);
			return util.exportExcel(list, "设备"+reportedQueryVo.getImei()+"实时数据");
		}else {
			List<DevHistoryThree> list = (List<DevHistoryThree>) map.get("data");
			ExcelUtil<DevHistoryThree> util = new ExcelUtil<DevHistoryThree>(DevHistoryThree.class);
			return util.exportExcel(list, "设备"+reportedQueryVo.getImei()+"实时数据");
		}
		
		
		
	}
}
