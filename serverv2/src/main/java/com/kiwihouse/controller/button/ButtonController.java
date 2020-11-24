package com.kiwihouse.controller.button;
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
import com.kiwihouse.common.bean.Code;
import com.kiwihouse.controller.common.BaseController;
import com.kiwihouse.dao.entity.Buttons;
import com.kiwihouse.domain.vo.Response;
import com.kiwihouse.dto.GroupDto;
import com.kiwihouse.service.ButtonService;
import com.kiwihouse.service.MenuBtnService;
import com.kiwihouse.vo.entire.Log;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "按钮操作")
@RestController
@RequestMapping("/button")
public class ButtonController extends BaseController{
	private static final Logger logger = LoggerFactory.getLogger(ButtonController.class);

	@Autowired
	MenuBtnService resourceButtonService;
	@Autowired
	ButtonService buttonService;
    @ApiOperation(value = "buttonListById",
            notes = "<br>@description: <b>根据页面ID查询按钮列表</b></br>" +
                    "<br>@Date: <b>2020-1-4 17:15:40</b></br>",
            httpMethod = "GET")
    @ApiResponses(@ApiResponse(code = 0,message ="回调参数：只有code和msg,无具体数据result"))
    @GetMapping("/info/{menuId}/{roleId}")
    public Response buttonListById(@PathVariable Integer menuId,@PathVariable Integer roleId,HttpServletRequest request){
        logger.info("根据页面ID查询按钮列表>> {}",new Log().setIp(request.getRemoteAddr()).setParam(menuId).setParam(roleId));
        Response resultList = resourceButtonService.buttonListById(menuId,roleId);
        logger.info("返回参数<< {}",resultList);
        return resultList;
    }
    
    @ApiOperation(value = "info",
            notes = "<br>@description: <b>查询按钮列表</b></br>" +
                    "<br>@Date: <b>2020-3-5 15:45:51</b></br>",
            httpMethod = "GET")
    @ApiResponses(@ApiResponse(code = 0,message ="回调参数",response = GroupDto.class))
    @GetMapping("/info")
    public Map<String, Object> queryInfo(Integer page,Integer limit,Integer trigger,Buttons buttons,HttpServletRequest request){
    	try {
    		logger.info("查询按钮列表>> {}",new Log().setIp(request.getRemoteAddr()).setParam(page).setParam(limit).setParam(trigger).setParam(buttons));
    		map = buttonService.queryInfo(page,limit,trigger, buttons);
    		map.put("code", 0);
    		map.put("msg",Code.QUERY_SUCCESS);
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("查询按钮列表>> {查询失败}",new Log().setIp(request.getRemoteAddr()).setParam(page).setParam(limit).setParam(trigger).setParam(buttons));
			return putMsgToJsonString(0, Code.QUERY_FAIL.getMsg(), 0, null);
		}
        return map;
    }

    @ApiOperation(value = "删除对应的角色的授权资源",httpMethod = "DELETE")
    @DeleteMapping("/{ids}")
    public Response deleteAuthorityRoleResource(@PathVariable String ids ,HttpServletRequest request) {
    	logger.info("删除对应的角色的授权资源>> {}",new Log().setIp(request.getRemoteAddr()).setParam(ids));
    	Response res = null;
    	try {
    		String [] idsArray = ids.split("_");
    		res = buttonService.deleteBatch(idsArray);
    		logger.info("删除对应的角色的授权资源>> {删除成功}");
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("删除对应的角色的授权资源>> {删除失败}");
			res = new Response().Fail(Code.DELETE_FAIL,Code.DELETE_FAIL.getMsg());
		}
    	 return res;
       
    }
    
    @ApiOperation(value = "addInfo",
            notes = "<br>@description: <b>添加按钮信息</b></br>" +
                    "<br>@Date: <b>2020-3-5 16:39:06</b></br>",
            httpMethod = "POST")
    @ApiResponses(@ApiResponse(code = 0,message ="回调参数：只有code和msg,无具体数据result"))
    @PostMapping("/add")
    public Response addInfo(@RequestBody @Validated Buttons resourceButtons, HttpServletRequest request){
        logger.info("添加按钮信息>> {}",new Log().setIp(request.getRemoteAddr()).setMsg("添加按钮信息").setParam(resourceButtons.toString()));
        Response res = null;
        try {
        	res = buttonService.insert(resourceButtons);
        	logger.info("添加按钮信息>> {添加成功}");
		} catch (Exception e) {
			// TODO: handle exception
			res = new Response().Success(Code.ADD_SUCCESS,Code.ADD_SUCCESS.getMsg());
			logger.info("添加按钮信息>> {添加失败}");
		}
        return res;
    }
    
    @ApiOperation(value = "update",
            notes = "<br>@description: <b>修改按钮信息</b></br>" +
                    "<br>@Date: <b>2020-3-5 16:39:06</b></br>",
            httpMethod = "PUT")
    @ApiResponses(@ApiResponse(code = 0,message ="回调参数：只有code和msg,无具体数据result"))
    @PutMapping("/upd")
    public Response updInfo(@RequestBody @Validated Buttons resourceButtons, HttpServletRequest request){
        logger.info("修改按钮信息>> {}",new Log().setIp(request.getRemoteAddr()).setMsg("修改按钮信息").setParam(resourceButtons.toString()));
        Response res = null;
        try {
        	res = buttonService.updateByPrimaryKey(resourceButtons);
        	logger.info("修改按钮信息>> {修改成功}");
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("修改按钮信息>> {修改失败}");
			res = new Response().Fail(Code.UPDATE_FAIL,Code.UPDATE_FAIL.getMsg());
		}
        return res;
    }
    
}
