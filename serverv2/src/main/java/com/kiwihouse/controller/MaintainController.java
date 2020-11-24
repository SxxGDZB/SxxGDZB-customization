package com.kiwihouse.controller;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.kiwihouse.common.bean.Code;
import com.kiwihouse.controller.common.BaseController;
import com.kiwihouse.domain.vo.Response;
import com.kiwihouse.dto.MtInfoDto;
import com.kiwihouse.service.CheckAdminService;
import com.kiwihouse.service.MaintainService;
import com.kiwihouse.util.excel.ExcelUtil;
import com.kiwihouse.util.file.FileUtils;
import com.kiwihouse.vo.entire.Log;
import com.kiwihouse.vo.entire.ResultList;
import com.kiwihouse.vo.kiwihouse.MtInfoVo;
import com.kiwihouse.vo.kiwihouse.MtUpdateVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @author yjzn
 * @date 2020-1-4 10:48:29
 */
@Api(tags = "维修记录")
@RestController
@RequestMapping("/maintain")
public class MaintainController extends BaseController{
    private static final Logger logger = LoggerFactory.getLogger(MaintainController.class);

    @Autowired
    MaintainService maintainService;
    @Autowired
    CheckAdminService checkAdminService;
    
    @SuppressWarnings("unused")
	@ApiOperation(value = "queryInfo",
            notes = "<br>@description: <b>查询维修信息信息</b></br>" +
                    "<br>@Date: <b>2020-1-4 13:45:00</b></br>",
            httpMethod = "GET")
    @ApiResponses({@ApiResponse(code = 0, message = "返回参数", response = MtInfoDto.class)})
    @GetMapping("/info")
    public Map<String, Object> queryInfo(@Validated MtInfoVo mtInfoVo, HttpServletRequest request){
        mtInfoVo.setPage(( mtInfoVo.getPage() - 1 ) * mtInfoVo.getLimit() );
//        try {
    		if(mtInfoVo == null) {
    			mtInfoVo = new MtInfoVo();
    		}
    		ResultList resultList = maintainService.queryInfo(mtInfoVo);
    		if(resultList.getResult() == null) {
    			map.put("data", null);
        		map.put("count", 0);
    		}else {
    			map.put("data", resultList.getResult().getData());
        		map.put("count", resultList.getResult().getRow());
    		}
    		
    		map.put("code", 0);
    		map.put("msg",Code.QUERY_SUCCESS);
//		} catch (Exception e) {
//			// TODO: handle exception
//			return putMsgToJsonString(0, Code.QUERY_FAIL.getMsg(), 0, null);
//		}
		return map;
    }

    @ApiOperation(value = "addInfo",
            notes = "<br>@description: <b>告警信息转工单</b></br>" +
                    "<br>@Date: <b>2020-1-4 17:15:40</b></br>",
            httpMethod = "POST")
    @ApiResponses(@ApiResponse(code = 0,message ="回调参数：只有code和msg,无具体数据result"))
    @PostMapping("/info/{alarmId}/{imei}/{mtType}")
    public Response addInfo(@PathVariable String alarmId,
                              @PathVariable String imei,
                              @PathVariable String mtType,
                              HttpServletRequest request){
        logger.info("告警转工单>> {}",new Log().setIp(request.getRemoteAddr()).setParam(alarmId));
        Response resultList = maintainService.addInfo(alarmId,imei,mtType);
        logger.info("返回参数<< {}",resultList);
        return resultList;
    }

    @ApiOperation(value = "updateInfo",
            notes = "<br>@description: <b>修改维修信息</b></br>" +
                    "<br>@Date: <b>2020-1-4 17:15:40</b></br>",
            httpMethod = "PUT")
    @ApiResponses(@ApiResponse(code = 0,message ="回调参数：只有code和msg,无具体数据result"))
    @PutMapping("/info")
    public Response updateInfo(@RequestBody @Validated MtUpdateVo mtUpdateVo,HttpServletRequest request){
        logger.info("修改维修信息>> {}",new Log().setIp(request.getRemoteAddr()).setParam(mtUpdateVo));
//        if(!checkAdminService.isMtIdBelong2Admin(mtUpdateVo.getMtId(),request.getHeader("dz-usr"))){
//            return new Response().Success(Code.PRIVILEGE_FAIL, Code.PRIVILEGE_FAIL.getMsg());
//        }
        Response resultList = maintainService.updateInfo(mtUpdateVo);
        logger.info("返回参数<< {}",resultList);
        return resultList;
    }
    
    @ApiOperation(value = "updateInfo",
            notes = "<br>@description: <b>Excel导出</b></br>" +
                    "<br>@Date: <b>2020-1-4 17:15:40</b></br>",
            httpMethod = "POST")
    @ApiResponses(@ApiResponse(code = 0,message ="回调参数：只有code和msg,无具体数据result"))
    @PostMapping("/export")
    public Response export(@RequestBody MtInfoVo mtInfoVo,HttpServletRequest request){
    	mtInfoVo.setLimit(null);
    	mtInfoVo.setPage(null);
    	ResultList resultList = maintainService.queryInfo(mtInfoVo);
        List<MtInfoDto> list = (List<MtInfoDto>) resultList.getResult().getData();
        ExcelUtil<MtInfoDto> util = new ExcelUtil<MtInfoDto>(MtInfoDto.class);
        return util.exportExcel(list, "维修记录");
    }
    @ApiOperation(value = "importData",
            notes = "<br>@description: <b>Excel导入</b></br>" +
                    "<br>@Date: <b>2020-1-4 17:15:40</b></br>",
            httpMethod = "POST")
    @ApiResponses(@ApiResponse(code = 0,message ="回调参数：只有code和msg,无具体数据result"))
    @PostMapping("/importData")
    @ResponseBody
    public Response importData(MultipartFile file) throws Exception
    {
        ExcelUtil<MtInfoDto> util = new ExcelUtil<MtInfoDto>(MtInfoDto.class);
        List<MtInfoDto> userList = util.importExcel(file.getInputStream());
        userList.forEach(ul ->{
        	System.out.println(ul.toString());
        });
        return maintainService.insertOrUpdateBatch(userList);
    }
    /**
     * 通用下载请求
     * 
     * @param fileName 文件名称
     * @param delete 是否删除
     */
    @GetMapping("common/download")
    public void fileDownload(String fileName, Boolean delete, HttpServletResponse response, HttpServletRequest request)
    {
        String realFileName = System.currentTimeMillis() + fileName.substring(fileName.indexOf("_") + 1);
        try
        {
            String filePath = downloadUrl +  fileName;
            response.setCharacterEncoding("utf-8");
            response.setContentType("multipart/form-data");
            response.setHeader("Content-Disposition",
                    "attachment;fileName=" + setFileDownloadHeader(request, realFileName));
            FileUtils.writeBytes(filePath, response.getOutputStream());
            if (delete)
            {
                FileUtils.deleteFile(filePath);
            }
        }
        catch (Exception e)
        {
            logger.info("下载文件失败<< {}",e);
        }
    }
    
}
