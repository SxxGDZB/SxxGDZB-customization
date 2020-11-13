package com.kiwihouse.controller.equipment;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.kiwihouse.common.bean.Code;
import com.kiwihouse.common.bean.UserInfo;
import com.kiwihouse.controller.common.BaseController;
import com.kiwihouse.dao.mapper.AlarmMapper;
import com.kiwihouse.dao.mapper.AuthUserMapper;
import com.kiwihouse.domain.vo.Response;
import com.kiwihouse.dto.Eqpt4UpdateDto;
import com.kiwihouse.dto.EqptInfoDto;
import com.kiwihouse.service.CheckAdminService;
import com.kiwihouse.service.EquipmentService;
import com.kiwihouse.util.excel.ExcelUtil;
import com.kiwihouse.util.file.FileUtils;
import com.kiwihouse.vo.entire.Log;
import com.kiwihouse.vo.kiwihouse.EqptAddVo;
import com.kiwihouse.vo.kiwihouse.EqptQueryVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * 设备信息接口
 *
 * @author yjzn
 * @date 2019-12-30 10:26:27
 */
@RestController
@RequestMapping("/equipment")
@Api(tags = "设备信息接口")
public class EquipmentController extends BaseController {
	private final Logger logger = LoggerFactory.getLogger(EquipmentController.class);

	@Autowired
	EquipmentService equipmentService;
	@Autowired
	CheckAdminService checkAdminService;
	@Autowired
	AuthUserMapper authUserMapper;
	@Autowired
	AlarmMapper alarmMapper;

	@ApiOperation(value = "queryInfo", notes = "<br>@description: <b>查询设备信息</b></br>"
			+ "<br></br>" + "<br>@Date: <b>2019-12-30 10:33:36</b></br>", httpMethod = "GET")
	@ApiResponses({ @ApiResponse(code = 0, message = "回调参数", response = EqptInfoDto.class) })
	@GetMapping("/info")
	public Map<String, Object> queryInfo(@Validated EqptQueryVo eqptQueryVo, HttpServletRequest request) {
		logger.info("查询设备信息>> {}", new Log().setIp(request.getRemoteAddr()).setParam(eqptQueryVo));
		return equipmentService.queryInfo(eqptQueryVo);
	}

	@ApiOperation(value = "updateInfo", notes = "<br>@description: <b>修改设备信息</b></br>"
			+ "<br>@Date: <b>2020-1-3 17:25:42</b></br>", httpMethod = "PUT")
	@ApiResponses(@ApiResponse(code = 0, message = "回调参数：只有code和msg,无具体数据result"))
	@PutMapping("/info")
	public Response updateInfo(@RequestBody @Validated Eqpt4UpdateDto updateDto, HttpServletRequest request) {
		logger.info("更新设备信息>> {}",
				new Log().setIp(request.getRemoteAddr()).setMsg("更新设备信息").setParam(updateDto.toString()));
		String adminId = request.getHeader("dz-usr");
		updateDto.setDoAdminId(adminId);
		return equipmentService.updateInfo(updateDto);
	}

	@ApiOperation(value = "addInfo", notes = "<br>@description: <b>添加设备信息</b></br>"
			+ "<br>@Date: <b>2020-1-3 17:25:42</b></br>", httpMethod = "POST")
	@ApiResponses(@ApiResponse(code = 0, message = "回调参数：只有code和msg,无具体数据result"))
	@PostMapping("/info")
	public Response addInfo(@RequestBody @Validated EqptAddVo eqptAddVo, HttpServletRequest request) {
		logger.info("录入设备信息>> {}",
				new Log().setIp(request.getRemoteAddr()).setMsg("添加设备信息").setParam(eqptAddVo.toString()));
		return equipmentService.addInfo(eqptAddVo);
	}

	@ApiOperation(value = "deleteInfo", notes = "<br>@description: <b>删除设备信息</b></br>"
			+ "<br>@Date: <b>2020-1-3 17:25:42</b></br>", httpMethod = "DELETE")
	@ApiResponses(@ApiResponse(code = 0, message = "回调参数：只有code和msg,无具体数据result"))
	@ApiImplicitParam(paramType = "path", name = "eqptSn", dataType = "String", required = true, value = "设备序列号")
	@DeleteMapping("/info/{imeis}")
	public Response deleteInfo(@PathVariable String imeis, HttpServletRequest request) {
		logger.info("删除设备信息>> {}", new Log().setIp(request.getRemoteAddr()).setMsg("删除设备信息").setParam(imeis));
		return equipmentService.deleteInfo(imeis, new UserInfo(request));
	}

	@ApiOperation(value = "selectOneInfo", notes = "<br>@description: <b>查询设备信息</b></br>"
			+ "<br>@Return: <b>以区为单位进行区分</b></br>" + "<br>@Date: <b>2019-12-30 10:33:36</b></br>", httpMethod = "GET")
	@ApiResponses({ @ApiResponse(code = 0, message = "回调参数", response = EqptInfoDto.class) })
	@GetMapping("/info/one")
	public Response selectOneInfo(EqptQueryVo eqptQueryVo, HttpServletRequest request) {
		return equipmentService.selectOneInfo(eqptQueryVo);
	}

	@ApiOperation(value = "export", notes = "<br>@description: <b>Excel导出</b></br>"
			+ "<br>@Date: <b>2020-1-4 17:15:40</b></br>", httpMethod = "Post")
	@ApiResponses(@ApiResponse(code = 0, message = "回调参数：只有code和msg,无具体数据result"))
	@PostMapping("/export")
	public Response export(@RequestBody EqptQueryVo eqptQueryVo, HttpServletRequest request) {
		logger.info("Excel导出设备信息>> {}", new Log().setIp(request.getRemoteAddr()).setParam(eqptQueryVo));
		eqptQueryVo.setLimit(null);
		Map<String, Object> map = equipmentService.queryInfo(eqptQueryVo);
		List<EqptInfoDto> list = (List<EqptInfoDto>) map.get("data");
		ExcelUtil<EqptInfoDto> util = new ExcelUtil<EqptInfoDto>(EqptInfoDto.class);
		return util.exportExcel(list, "设备列表");
	}

	@ApiOperation(value = "importData", notes = "<br>@description: <b>Excel导入</b></br>"
			+ "<br>@Date: <b>2020-1-4 17:15:40</b></br>", httpMethod = "Post")
	@ApiResponses(@ApiResponse(code = 0, message = "回调参数：只有code和msg,无具体数据result"))
	@PostMapping("/importData")
	@ResponseBody
	public Response importData(MultipartFile file, HttpServletRequest request) throws Exception {
		logger.info("Excel导入设备信息>> {}", new Log().setIp(request.getRemoteAddr()).setParam(file.getName()));
		ExcelUtil<EqptInfoDto> util = new ExcelUtil<EqptInfoDto>(EqptInfoDto.class);
		List<EqptInfoDto> userList = util.importExcel(file.getInputStream());
		userList.forEach(ul -> {
			System.out.println(ul.toString());
		});
		return equipmentService.insertOrUpdateBatch(userList);
	}

	/**
	 * 通用下载请求
	 * 
	 * @param fileName 文件名称
	 * @param delete   是否删除
	 */
	@GetMapping("common/download")
	public void fileDownload(String fileName, Boolean delete, HttpServletResponse response,
			HttpServletRequest request) {
		String realFileName = System.currentTimeMillis() + fileName.substring(fileName.indexOf("_") + 1);
		try {
			String filePath = downloadUrl + fileName;
			response.setCharacterEncoding("utf-8");
			response.setContentType("multipart/form-data");
			response.setHeader("Content-Disposition",
					"attachment;fileName=" + setFileDownloadHeader(request, realFileName));
			FileUtils.writeBytes(filePath, response.getOutputStream());
			if (delete) {
				FileUtils.deleteFile(filePath);
			}
		} catch (Exception e) {
			logger.info("下载文件失败<< {}", e);
		}
	}

	@ApiOperation(value = "clearDevAlarms", notes = "<br>@description: <b>清除设备告警信息</b></br>"
			+ "<br>@Date: <b>2020-1-3 17:25:42</b></br>", httpMethod = "GET")
	@ApiResponses(@ApiResponse(code = 0, message = "回调参数：只有code和msg,无具体数据result"))
	@ApiImplicitParam(paramType = "path", name = "eqptSn", dataType = "String", required = true, value = "设备序列号")
	@GetMapping("/clear/alarm/{imei}")
	public Response clearDevAlarms(@PathVariable String imei, HttpServletRequest request) {
		logger.info("清除设备告警信息>> {}", new Log().setIp(request.getRemoteAddr()).setMsg("清除设备告警信息").setParam(imei));
		int count = 0;
		try {
			count = alarmMapper.clearDevAlarms(imei);
			if (count > 0) {
				return new Response().Success(Code.CLEAR_SUCCESS, Code.CLEAR_SUCCESS.getMsg());
			}
			return new Response().Fail(Code.CLEAR_NULL, Code.CLEAR_NULL.getMsg());
		} catch (Exception e) {
			// TODO: handle exception
			return new Response().Fail(Code.CLEAR_FAIL, Code.CLEAR_FAIL.getMsg());
		}
	}

	@ApiOperation(value = "queryInfo", notes = "<br>@description: <b>查询角色设备列表</b></br>"
			+ "<br>@Return: <b>以区为单位进行区分</b></br>" + "<br>@Date: <b>2019-12-30 10:33:36</b></br>", httpMethod = "GET")
	@ApiResponses({ @ApiResponse(code = 0, message = "回调参数", response = EqptInfoDto.class) })
	@GetMapping("/all")
	public Map<String, Object> queryRoleDevList(@Validated EqptQueryVo eqptQueryVo, HttpServletRequest request) {
		logger.info("查询角色设备列表>> {}", new Log().setIp(request.getRemoteAddr()).setParam(eqptQueryVo));
		return equipmentService.queryRoleDevList(eqptQueryVo);
	}

	@ApiOperation(value = "queryInfo", notes = "<br>@description: <b>查询用户设备列表</b></br>"
			+ "<br>@Return: <b>以区为单位进行区分</b></br>" + "<br>@Date: <b>2019-12-30 10:33:36</b></br>", httpMethod = "GET")
	@ApiResponses({ @ApiResponse(code = 0, message = "回调参数", response = EqptInfoDto.class) })
	@GetMapping("/user/all")
	public Map<String, Object> queryUserDevList(@Validated EqptQueryVo eqptQueryVo, HttpServletRequest request) {
		logger.info("查询用户设备列表>> {}", new Log().setIp(request.getRemoteAddr()).setParam(eqptQueryVo));
		return equipmentService.queryUserDevList(eqptQueryVo);
	}

	@ApiOperation(value = "/userId/dev", notes = "<br>@description: <b>修改用户设备列表</b></br>"
			+ "<br>@Date: <b>2020-1-3 17:25:42</b></br>", httpMethod = "PUT")
	@ApiResponses(@ApiResponse(code = 0, message = "回调参数：只有code和msg,无具体数据result"))
	@PutMapping("/{userId}/{deptIds}")
	public Response updateRoleDevList(@PathVariable @Validated Integer userId, @PathVariable @Validated String deptIds,
			HttpServletRequest request) {
		logger.info("修改用户设备列表>> {}",
				new Log().setIp(request.getRemoteAddr()).setMsg("修改用户设备列表").setParam(userId).setParam(deptIds));
		return equipmentService.updateUserDevList(userId, deptIds);
	}
}