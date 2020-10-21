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
        logger.info("告警转工单>> {}",new Log().setIp(request.getRemoteAddr()).setParam(menuId).setParam(roleId));
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
    public Map<String, Object> queryInfo(Integer page,Integer limit,Integer trigger,Buttons buttons){
    	try {
    		map = buttonService.queryInfo(page,limit,trigger, buttons);
    		map.put("code", 0);
    		map.put("msg",Code.QUERY_SUCCESS);
		} catch (Exception e) {
			// TODO: handle exception
			return putMsgToJsonString(0, Code.QUERY_FAIL.getMsg(), 0, null);
		}
        return map;
    }

    @ApiOperation(value = "删除对应的角色的授权资源",httpMethod = "DELETE")
    @DeleteMapping("/{ids}")
    public Response deleteAuthorityRoleResource(@PathVariable String ids ) {
    	String [] idsArray = ids.split("_");
        return buttonService.deleteBatch(idsArray);
    }
    
    @ApiOperation(value = "addInfo",
            notes = "<br>@description: <b>添加按钮信息</b></br>" +
                    "<br>@Date: <b>2020-3-5 16:39:06</b></br>",
            httpMethod = "POST")
    @ApiResponses(@ApiResponse(code = 0,message ="回调参数：只有code和msg,无具体数据result"))
    @PostMapping("/info")
    public Response addInfo(@RequestBody @Validated Buttons resourceButtons, HttpServletRequest request){
        logger.info("添加按钮信息>> {}",new Log().setIp(request.getRemoteAddr()).setMsg("添加按钮信息").setParam(resourceButtons.toString()));
        return buttonService.insert(resourceButtons);
    }
    
    @ApiOperation(value = "update",
            notes = "<br>@description: <b>添加按钮信息</b></br>" +
                    "<br>@Date: <b>2020-3-5 16:39:06</b></br>",
            httpMethod = "POST")
    @ApiResponses(@ApiResponse(code = 0,message ="回调参数：只有code和msg,无具体数据result"))
    @PutMapping("/info")
    public Response updInfo(@RequestBody @Validated Buttons resourceButtons, HttpServletRequest request){
        logger.info("修改按钮信息>> {}",new Log().setIp(request.getRemoteAddr()).setMsg("修改按钮信息").setParam(resourceButtons.toString()));
        return buttonService.updateByPrimaryKey(resourceButtons);
    }
    
}
