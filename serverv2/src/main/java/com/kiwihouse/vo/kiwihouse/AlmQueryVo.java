package com.kiwihouse.vo.kiwihouse;

import javax.validation.constraints.Min;

import com.kiwihouse.common.annotation.date.Date;
import com.kiwihouse.common.annotation.naturalNumber.NaturalNumber;
import com.kiwihouse.common.bean.Basic;
import com.kiwihouse.util.excel.Excel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @author yjzn
 * @date 2020-1-9 19:36:28
 */
@Accessors(chain = true)
@ToString
@Getter
@Setter
@ApiModel(description = "查询告警信息参数")
public class AlmQueryVo {

    @NaturalNumber
    @ApiModelProperty(value = "区域编码",name = "code")
    private String code;

    @ApiModelProperty(value = "详细地址",name = "address")
    private String address;

    @Excel(name = "用户ID")
    @NaturalNumber(message = "userId must be natural number")
    @ApiModelProperty(value = "用户ID",name = "userId")
    private String userId;

    @Excel(name = "告警信息状态")
    @ApiModelProperty(value = "告警信息状态(-1-撤销告警，0-未处理，1-已转为工单，2-已处理)",name = "alarmStatus")
    @Min(value = -1,message = "alarmStatus is in [-1,2]")
    private String alarmStatus;
    
    @Excel(name = "设备序列号")
    @ApiModelProperty(value = "设备序列号",name = "eqptSn")
    private String eqptSn;

    @Excel(name = "开始时间")
    @Date
    @ApiModelProperty(value = "开始时间",name ="startTime",required =false)
    private String startTime;
    
    @Excel(name = "结束时间")
    @Date
    @ApiModelProperty(value = "结束时间",name ="endTime",required =false)
    private String endTime;

    private Integer page;

    private Integer limit;

    @ApiModelProperty(hidden = true)
    private String province;
    @ApiModelProperty(hidden = true)
    private String city;
    @ApiModelProperty(hidden = true)
    private String district;
    @ApiModelProperty(value = "imei",name = "imei")
    private String imei;
    /**
     * 0  ---->默认倒叙
     * 1  ---->正序
     */
    private Integer orderBy;
    
    private String eqptAddr;
    
    private Integer alarmType;
    
    private String addTime;
    
    private String eqptType;
    private String roleId;
    
    
}
