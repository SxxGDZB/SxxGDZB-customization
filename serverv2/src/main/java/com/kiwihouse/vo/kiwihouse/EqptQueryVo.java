package com.kiwihouse.vo.kiwihouse;

import java.util.Date;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.kiwihouse.common.annotation.code.Code;
import com.kiwihouse.common.annotation.naturalNumber.NaturalNumber;
import com.kiwihouse.common.bean.Basic;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author yjzn
 * @date 2019-12-30-上午 10:38
 */
@ToString
@Getter
@Setter
@ApiModel(description = "查询设备信息参数")
public class EqptQueryVo extends Basic {

    @Code
    @ApiModelProperty(value = "区域编码(6位)",name = "code",required =false,example ="440000")
    private String code;

    @ApiModelProperty(value = "县乡镇",name = "address",required = false)
    private String address;

    @ApiModelProperty(value = "详细地址",name = "eqptAddr",required = false)
    private String eqptAddr;

    @ApiModelProperty(value = "设备序列号",name = "eqptSn",required =false)
    private String eqptSn;

    @ApiModelProperty(value = "查询设备是否在线(1:在线,-1:不在线)",name = "online",required =false)
    private String online;

    @ApiModelProperty(value = "分组ID",name = "groupId",required =false)
    private String groupId;

    @NaturalNumber(message = "eqptType(设备类型) is natural number")
    @ApiModelProperty(value = "设备类型(0-火警设备,1-烟感设备)",name = "eqptType",required =false,allowableValues = "0,1")
    private String eqptType;

    @ApiModelProperty(value = "用户电话号码",name = "userPhone",required =false)
    private String userPhone;

    @ApiModelProperty(value = "用户姓名",name = "userName",required =false)
    private String userName;

//    @ApiModelProperty(value = "页码",name = "page",required =true,example ="1")
//    @NotNull(message = "page is not null")
    //@Min(value = 1,message = "page最小为1")
    private Integer page;

//    @ApiModelProperty(value = "每页条数",name = "limit",required =true,example ="10")
//    @NotNull(message = "limit is not null")
//    @Min(value = 1,message = "limit最小为10")
    private Integer limit;
    /**
     * imei号
     */
    private String imei;
    @ApiModelProperty(hidden = true)
    private String province;
    @ApiModelProperty(hidden = true)
    private String city;
    @ApiModelProperty(hidden = true)
    private String district;
    @ApiModelProperty(hidden = true)
    private String[] groupIds;
    /**
     * 	设备名称
     */
    @ApiModelProperty(value = "设备名称",name = "eqptName",required =false)
    private String eqptName;
    /**
     * 	抄表时间
     */
    private Date addTime;
    
    private Integer userId;
    
    private Integer adminId;
    
}
