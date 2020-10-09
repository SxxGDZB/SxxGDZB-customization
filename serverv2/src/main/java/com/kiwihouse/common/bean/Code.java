package com.kiwihouse.common.bean;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.ToString;

/**
 * 1000~1999 添加状态码
 * 2000~2999 删除状态码
 * 3000~3999 更新状态码
 * 4000~4999 查询状态码
 * 5000~5999 校验状态码
 * 6000~6999 权限状态码
 * 7000~7999 登录状态码
 * 8000~8999 其他状态码
 *
 * @author xin
 * @date 2019-12-19-下午 5:00
 */
@ToString
@Getter
@ApiModel(description = "接口响应码")
public enum Code {
	/*
	 * REQUEST_ERROR(9999, "错误请求"),
	 * 
	 * INSERT_SUCCESS(1000, "增加成功"), INSERT_FAIL(1001, "增加失败"),
	 * 
	 * DEL_SUCCESS(2000, "删除成功"), DEL_FAIL(2001, "删除失败"),
	 * 
	 * UPDATE_SUCCESS(3000, "更新成功"), UPDATE_FAIL(3001, "更新失败"),
	 * 
	 * 
	 * READ_SUCCESS(4000, "查询成功"), READ_FAIL(4001, "查询失败"),
	 * 
	 * VERIFY_SUCCESS(5000, ""), VERIFY_FAIL(5001, ""),
	 * 
	 * PERMISSION_NO(6001, "无权限"),
	 * 
	 * LOGIN_SUCCESS(7000, "登录成功"), LOGIN_FAIL(7001, "登录失败"),
	 * 
	 * PARAM_FORMAT_ERROR(8001, "参数格式错误"), EXECUTION_ERROR(8002, "执行失败"),
	 */
	REQUEST_ERROR(9999, "错误请求"),
	PERMISSION_NO(6001, "无权限"),
	UPLOAD_IMAGE_SUCC(6500,"图片上传成功"),
    UPLOAD_IMAGE_FAIL(6501,"图片上传失败"),
    UPLOAD_IMAGE_FILENAME_ERROR(6502,"图片文件名有误"),

    PARAM_FORMAT_ERROR(2004, "参数不正确"),
    EXECUTION_ERROR(6002, "接口执行失败"),
    CUSTOM_EXCEPTION(6022, "自定义异常"),

    QUERY_SUCCESS(2000,"数据查询成功"),
    QUERY_NULL(2008, "数据查询为空"),
    QUERY_FAIL(2009, "数据查询失败"),

    UPDATE_SUCCESS(3000,"数据更新成功"),
    UPDATE_NULL(3003,"更新数据不存在"),
    UPDATE_FAIL(3004,"数据更新失败"),

    DELETE_SUCCESS(4000,"数据删除成功"),
    DELETE_NULL(4003,"数据删除不存在"),
    DELETE_FAIL(4004,"数据删除失败"),
    DELETE_UNSAFE(4005,"不安全数据删除"),

    CLEAR_SUCCESS(4099,"数据清除成功"),
    CLEAR_FAIL(4100,"数据清除失败"),
    CLEAR_NULL(4101,"数据为空"),
    
    ADD_SUCCESS(5000,"数据录入成功"),
    ADD_REPETITIVE(5003,"添加数据重复"),
    ADD_FAIL(5004,"数据录入失败"),
    QUARTZ_REPETITIVE(5005,"添加重复的定时任务"),

    VERIFY_SUCCESS(6000,"接口校验成功"),
    VERIFY_FAIL(6001,"接口校验失败"),
    VERIFY_NULL(6004,"接口校验请求头为空"),

    SENDSMS_SUCC(6100,"短信发送成功"),
    SENDSMS_FAIL(6101,"短信发送失败"),

    PRIVILEGE_SUCCESS(7000,"权限校验成功"),
    PRIVILEGE_FAIL(7001,"没有访问权限"),
    PRIVILEGE_NULL(7004,"dz-usr请求头为空"),
    PRIVILEGE_ALL(7005,"拥有所有权限"),
    COMMAND_FAIL(7999,"命令下发失败"),
    COMMAND_ISSUED(8000,"命令下发完成"),
    REGISTER_SUCC(8001,"设备注册成功"),
    LOGIN_SUCC(8002,"用户登录成功"),
    LOGIN_FAIL(8003,"账号或密码错误"),
    UPDATE_PASSWORD_FAIL_PASSWOR_DWRONG(80003,"原密码不正确"),
    UPDATE_PASSWORD_SUCCESS(80004,"密码修改成功"),
    UPDATE_PASSWORD_FAIL(80004,"系统错误,修改失败"),
    LOGIN_NULL(8004,"登录用户不存在,请联系管理员"),
    LOGIN_REPLACE(8005,"登录顶替"),
    LOGIN_DUE(8006,"登录失效"),
    LOGIN_NO(8007,"用户未登录"),
    EXIT_SUCC(8008,"用户退出系统成功"),

    ONLINE(9000,"设备在线"),
    NOTONLINE(9001,"设备不在线"),
    DEVICEID_INEXISTENCE(9002,"deviceId不存在"),

    EXCEL_LEAD_OUT_SUCCESS(100001,"Excel导出成功"),
    EXCEL_LEAD_OUT_FAIL(100002,"导出Excel失败，请联系网站管理员！"),
    EXCEL_LEAD_IN_SUCCESS(100003,"导入Excel成功"),
    EXCEL_LEAD_IN_FAIL(100004,"导入Excel失败，请联系网站管理员！"),
    RECOVER_SUCCESS(8090,"恢复成功"),
    RECOVER_FAIL(8091,"恢复失败");
	
	
    //枚举的属性字段必须是私有且不可变
    private final int code;
    private final String msg;

    Code(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
