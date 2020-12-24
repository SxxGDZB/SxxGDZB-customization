package com.kiwihouse.controller.account.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * @author xin
 * @date 2020/7/16
 */
@Data
@ApiModel(description = "登录注册信息")
@ToString
@Setter
@Getter
public class UserParams {
    @ApiModelProperty(value = "用户名",name = "username",required =true,position =1)
    private String username;
    @ApiModelProperty(value = "密码",name = "password",required =true,position =2)
    private String password;
    @ApiModelProperty(value = "时间戳",name = "timestamp",required =false,hidden = true)
    private long timestamp;
    @ApiModelProperty(value = "unionId",name = "unionId",required =false,hidden = true)
    private String unionId;
    @ApiModelProperty(value = "openId",name = "openId",required =false,hidden = true)
    private String openId;
    private String phone;
    @ApiModelProperty(value = "盐值",name = "salt",hidden = true)
    private String salt;
}
