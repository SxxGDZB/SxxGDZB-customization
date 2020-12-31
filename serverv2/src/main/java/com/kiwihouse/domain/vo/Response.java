package com.kiwihouse.domain.vo;

import com.kiwihouse.common.bean.Code;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 前后端统一消息定义协议 Message  之后前后端数据交互都按照规定的类型进行交互
 * {
 * meta:{"code":code,“msg”:message}
 * data:{....}
 * }
 *
 * @author xin
 * @date 10:48 2020/07/14
 */
@Data
public class Response {


    /**
     * 消息内容  存储实体交互数据
     */
    private Map<String, Object> data = new HashMap<String, Object>();


    private Integer code;
    private String msg;
    private boolean success;
    private long timestamp;
    public Response addData(String key, Object value) {
        this.data.put(key, value);
        return this;
    }

    public Response Success(int statusCode, String statusMsg) {
        this.success = true;
        this.code = statusCode;
        this.msg = statusMsg;
        this.timestamp = System.currentTimeMillis();
        return this;
    }

    public Response Success(Code statusCode, String statusMsg) {
        return Success(statusCode.getCode(), statusMsg);
    }

    public Response Success(Code statusCode) {
        return Success(statusCode.getCode(), statusCode.getMsg());
    }

    public Response Fail(int statusCode, String statusMsg) {
        this.success = false;
        this.code = statusCode;
        this.msg = statusMsg;
        this.timestamp = System.currentTimeMillis();
        return this;
    }

    public Response Fail(Code statusCode, String statusMsg) {
        return Fail(statusCode.getCode(), statusMsg);
    }

    public Response Fail(Code statusCode) {
        return Fail(statusCode.getCode(), statusCode.getMsg());
    }

}
