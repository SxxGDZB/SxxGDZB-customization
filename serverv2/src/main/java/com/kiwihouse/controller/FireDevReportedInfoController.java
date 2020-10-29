package com.kiwihouse.controller;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kiwihouse.common.bean.Code;
import com.kiwihouse.common.bean.DataType;
import com.kiwihouse.common.bean.EqptTypeSta;
import com.kiwihouse.common.utils.TimeUtil;
import com.kiwihouse.controller.common.BaseController;
import com.kiwihouse.dao.mapper.EquipmentMapper;
import com.kiwihouse.domain.vo.Response;
import com.kiwihouse.dto.AlarmEqptDto;
import com.kiwihouse.dto.EqptInfoDto;
import com.kiwihouse.dto.ReportedDto;
import com.kiwihouse.service.CheckAdminService;
import com.kiwihouse.service.DevInfoService;
import com.kiwihouse.service.ReportedInfoService;
import com.kiwihouse.service.ThreePhaseService;
import com.kiwihouse.util.excel.ExcelUtil;
import com.kiwihouse.vo.kiwihouse.AlmQueryVo;
import com.kiwihouse.vo.kiwihouse.QueryPwrVo;
import com.kiwihouse.vo.kiwihouse.ReportedQueryVo;
import com.kiwihouse.vo.kiwihouse.ThreePhaseVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @author yjzn
 * @date 2020-1-3 15:37:06
 */
@RestController
@RequestMapping("/receive")
@Api(tags = "火警上报信息")
public class FireDevReportedInfoController extends BaseController{

    @Autowired
    ReportedInfoService reportedInfoService;
    @Autowired
    CheckAdminService checkAdminService;
    @Autowired
    EquipmentMapper equipmentMapper;
    @Autowired
    ThreePhaseService threePhaseService;
    @Autowired
    DevInfoService devInfoService;
    @ApiOperation(value = "queryInfo",
            notes = "<br>@description: <b>查询设备上报信息</b></br>" +
                    "<br>@Date: <b>2020-1-3 15:36:22</b></br>",
            httpMethod = "GET")
    @ApiResponses({@ApiResponse(code = 0, message = "返回参数", response = ReportedDto.class)})
    @GetMapping(value = "/info")
    public Response queryInfo(@Validated ReportedQueryVo reportedQueryVo, HttpServletRequest request) {
        //checkAdminService.verifyAdminId(request.getHeader("dz-usr"), reportedQueryVo);
    	reportedQueryVo.setUserId(request.getHeader("dz-usr"));
        EqptInfoDto eqptInfo = equipmentMapper.queryInfoByImei(reportedQueryVo.getImei());
        if (eqptInfo.getEqptType().equals(DataType.THREE_PHASE.toString())) {
        	if(reportedQueryVo.getAlarmType().equals(DataType.DEV_PARAMETER)) {
        		//三相设备 ----------->参数数据  
        		return reportedInfoService.devRunInfo(reportedQueryVo,Integer.valueOf(DataType.DEV_PARAMETER));
        	}else if(DataType.DEV_REAL_DATE.equals(reportedQueryVo.getAlarmType())){
        		//三相设备 ----------->实时数据
        		return devInfoService.selectDevByNewTime(reportedQueryVo.getImei(),Integer.valueOf(DataType.DEV_REAL_DATE),DataType.THREE_PHASE);
        	}else {
        		return null;
        	}
        } else {
        	if(DataType.DEV_PARAMETER.equals(reportedQueryVo.getAlarmType())) {
        		//单相设备  ------->参数数据
        		return reportedInfoService.devRunInfo(reportedQueryVo,Integer.valueOf(DataType.DEV_PARAMETER));
        	}
        	else if(DataType.DEV_REAL_DATE.equals(reportedQueryVo.getAlarmType())){
        		//单相设备  ------->实时数据
        		return devInfoService.selectDevByNewTime(reportedQueryVo.getImei(),Integer.valueOf(DataType.DEV_REAL_DATE),DataType.ONE_PHASE);
        	}else {
        		return reportedInfoService.devRealDate(reportedQueryVo,Integer.valueOf(DataType.DEV_REAL_DATE));
        	}
        }
    }
  
    @ApiOperation(value = "queryAlmInfo",
            notes = "<br>@description: <b>查询设备告警信息</b></br>" +
                    "<br>@Date: <b>2020-1-9 19:57:11</b></br>",
            httpMethod = "GET")
    @ApiResponses({@ApiResponse(code = 0, message = "返回参数", response = AlarmEqptDto.class)})
    @GetMapping(value = "/alarm/info")
    public  Map<String, Object>  queryAlmInfo(@Validated AlmQueryVo almQueryVo ,HttpServletRequest request) {
        //checkAdminService.verifyAdminId(request.getHeader("dz-usr"), almQueryVo);
//    	try {
    		map =  reportedInfoService.queryAlmInfo(almQueryVo);
    		map.put("code", 0);
    		map.put("msg",Code.QUERY_SUCCESS);
//		} catch (Exception e) {
			// TODO: handle exception
//			return putMsgToJsonString(0, Code.QUERY_NULL.getMsg(), 0, null);
//		}
        return map;
    }

    @ApiOperation(value = "queryLatestAlmInfo",
            notes = "<br>@description: <b>查询过去15内的告警数据</b></br>" +
                    "<br>@Date: <b>2020-3-25 09:44:13</b></br>",
            httpMethod = "GET")
    @ApiResponses({@ApiResponse(code = 0, message = "返回参数", response = AlarmEqptDto.class)})
    @GetMapping(value = "/latestalarm/info/{roleId}/{second}/{page}/{limit}")
    public Map<String, Object> queryLatestAlmInfo(@PathVariable int page,@PathVariable Integer roleId, @PathVariable int limit, @PathVariable int second, HttpServletRequest request) {

        String currentTime = TimeUtil.getCurrentTime();
        String passSecTime = TimeUtil.getPassSecTime(currentTime, -second);
        System.out.println("endTime--------->" + currentTime);
        System.out.println("startTime--------->" + passSecTime);
        AlmQueryVo almQueryVo = new AlmQueryVo()
                .setStartTime(passSecTime)
                .setEndTime(currentTime)
                .setAlarmStatus("0")
                .setRoleId(roleId.toString())
                .setPage(page)
                .setLimit(limit);
        return queryAlmInfo(almQueryVo, request);
    }
    
    @ApiOperation(value = "queryPwr",
            notes = "<br>@description: <b>查询设备上报功率数据</b></br>" +
                    "<br>@Date: <b>2020-3-30 13:32:52</b></br>",
            httpMethod = "GET")
    @ApiResponses({@ApiResponse(code = 0, message = "返回参数", response = ReportedDto.class)})
    @GetMapping(value = "/pwr/info")
    public Response queryPwr(@Validated QueryPwrVo queryPwrVo, HttpServletRequest request) throws ParseException {
        //checkAdminService.verifyAdminId(request.getHeader("dz-usr"), queryPwrVo);

        EqptInfoDto eqptInfo = equipmentMapper.queryInfoByImei(queryPwrVo.getImei());
        if (eqptInfo.getEqptType().equals(EqptTypeSta.SX)) {
            ThreePhaseVo tpv = new ThreePhaseVo();
            tpv.setImei(eqptInfo.getImei());
            tpv.setDataType(queryPwrVo.getType());
            tpv.setBeginTime(queryPwrVo.getStartTime());
            tpv.setEndTime(queryPwrVo.getEndTime());
            return threePhaseService.getMaxPowerList(tpv);
        } else {
            return reportedInfoService.queryPwr(queryPwrVo);
        }

    }
    /**
     * 解决分页Limit太大  mysql全表检索导致效率 慢 
     * @param count 总记录条数
     * @return
     */
    public Integer pageLimit(int count) {
    	
    	
		return count;
    	
    }
    
    
    @ApiOperation(value = "export",
            notes = "<br>@description: <b>告警记录Excel导出</b></br>" +
                    "<br>@Date: <b>2020-1-4 17:15:40</b></br>",
            httpMethod = "POST")
    @ApiResponses(@ApiResponse(code = 0,message ="回调参数：只有code和msg,无具体数据result"))
    @PostMapping("/export")
    public Response export(@RequestBody AlmQueryVo almQueryVo,HttpServletRequest request){
    	almQueryVo.setLimit(null);
    	almQueryVo.setPage(null);
    	map =  reportedInfoService.queryAlmInfo(almQueryVo);
        List<AlarmEqptDto> list = (List<AlarmEqptDto>) map.get("data");
        ExcelUtil<AlarmEqptDto> util = new ExcelUtil<AlarmEqptDto>(AlarmEqptDto.class);
        return util.exportExcel(list, "告警记录");
    }
}
