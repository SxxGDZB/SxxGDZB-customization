package com.kiwihouse.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kiwihouse.common.bean.Code;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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
@ApiModel(value = "返回类")
public class ResponseList<T> {


    /**
     * 消息内容  存储实体交互数据
     */
	@ApiModelProperty(value = "对象")
    private List<T> data;
	@ApiModelProperty(value = "状态码")
    private Integer code;
	@ApiModelProperty(value = "消息")
    private String msg;
	@ApiModelProperty(value = "是否成功")
    private boolean success;
	@ApiModelProperty(value = "时间戳")
    private long timestamp;
	@ApiModelProperty(value = "记录数")
    private Integer totalCount;
	

	public ResponseList<T> Success(int statusCode, String statusMsg,List<T> obj) {
        this.success = true;
        this.code = statusCode;
        this.msg = statusMsg;
        this.data = obj;
        this.timestamp = System.currentTimeMillis();
        return this;
    }
    public ResponseList<T>  Success(int statusCode, String statusMsg) {
        this.success = true;
        this.code = statusCode;
        this.msg = statusMsg;
        this.timestamp = System.currentTimeMillis();
        return this;
    }
    public ResponseList<T> Success(int statusCode, String statusMsg,List<T> obj,Integer totalCount) {
        this.success = true;
        this.code = statusCode;
        this.msg = statusMsg;
        this.totalCount = totalCount;
        this.data = obj;
        this.timestamp = System.currentTimeMillis();
        return this;
    }
    /**
     * 	返回一个对象
     * @param statusCode
     * @param statusMsg
     * @param obj
     * @return
     */
    public ResponseList<T>  Success(Code statusCode, String statusMsg,List<T> obj) {
        return Success(statusCode.getCode(), statusMsg,obj);
    }
    
    public ResponseList<T>  Success(Code statusCode, String statusMsg,List<T> obj,Integer totalCount) {
        return Success(statusCode.getCode(), statusMsg, obj,totalCount);
    }
    /**
     * 	返回状态以及消息
     * @param statusCode
     * @param statusMsg
     * @return
     */
    public ResponseList<T> Success(Code statusCode, String statusMsg) {
        return Success(statusCode.getCode(), statusMsg);
    }
    /**
     * 	返回状态以及消息
     * @param statusCode
     * @return
     */
    public ResponseList<T> Success(Code statusCode) {
        return Success(statusCode.getCode(), statusCode.getMsg());
    }

    public ResponseList<T> Fail(int statusCode, String statusMsg) {
        this.success = false;
        this.code = statusCode;
        this.msg = statusMsg;
        this.timestamp = System.currentTimeMillis();
        return this;
    }

    public ResponseList<T> Fail(Code statusCode, String statusMsg) {
        return Fail(statusCode.getCode(), statusMsg);
    }

    public ResponseList<T> Fail(Code statusCode) {
        return Fail(statusCode.getCode(), statusCode.getMsg());
    }

	

}
