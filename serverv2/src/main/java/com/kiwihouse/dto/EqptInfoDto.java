package com.kiwihouse.dto;

import java.util.List;

import com.kiwihouse.util.excel.Excel;
import com.kiwihouse.util.excel.Excel.Type;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author yjzn
 * @date 2019-12-30-下午 1:47
 */
@ToString
@Setter
@Getter
@ApiModel(description = "查询设备信息参数")
public class EqptInfoDto {

    
    @Excel(name = "设备ID")
    @ApiModelProperty(value = "系统生成设备ID")
    private Integer eqptId;
    
    @ApiModelProperty(value = "onenet平台生成设备ID")
    private String deviceId;
    
    @ApiModelProperty(value = "设备序列号(SN号)")
    @Excel(name = "设备序列号(SN号)")
    private String eqptSn;
    
    @Excel(name = "设备IMEI号")
    @ApiModelProperty(value = "设备IMEI号")
    private String imei;
    
    @Excel(name = "设备IMSI号")
    @ApiModelProperty(value = "设备IMSI号")
    private String imsi;
    
    @Excel(name = "设备名称")
    @ApiModelProperty(value = "设备名称")
    private String eqptName;
    
    @Excel(name = "设备类型",readConverterExp = "0=单相用电设备,1=三相用电设备,2=烟感告警器")
    @ApiModelProperty(value = "设备类型(0:用电告警器,1:烟雾告警器)")
    private String eqptType;
    
//    @Excel(name = "是否注册到onenet平台",defaultValue = "0",readConverterExp = "0=否,1=是")
//    @ApiModelProperty(value = "是否注册到onenet平台")
//    private String electricity;
    
    @ApiModelProperty(value = "设备在线状态(9000:在线,9001:掉线)")
    private String eqptStatus;
    
    @Excel(name= "省")
    @ApiModelProperty(value = "省")
    private String province;
    
    @Excel(name= "市")
    @ApiModelProperty(value = "市")
    private String city;
    
    @Excel(name= "区")
    @ApiModelProperty(value = "区")
    private String district;
    
    @Excel(name= "设备详细地址")
    @ApiModelProperty(value = "与设备一一对应详细地址")
    private String eqptAddr;
    
    @Excel(name= "经度")
    @ApiModelProperty(value = "经度")
    private String longitude;
    
    @Excel(name= "纬度")
    @ApiModelProperty(value = "纬度")
    private String latitude;
    
    @Excel(name = "用户ID",defaultValue = "")
    @ApiModelProperty(value = "用户ID")
    private Integer userId;
    
    @Excel(name = "用户姓名")
    @ApiModelProperty(value = "用户姓名")
    private String userName;
    
    @Excel(name = "角色ID",width = 30)
    @ApiModelProperty(value = "角色ID")
    private Integer roleId;
    
//    @Excel(name = "小区ID")
//    @ApiModelProperty(value = "地址ID")
//    private String siteId;
    
    @ApiModelProperty(value = "管理员ID")
    private String adminId;
    
    @Excel(name = "分组ID")
    @ApiModelProperty(value = "设备分组ID")
    private Integer groupId;
    
    @Excel(name = "设备分组名称")
    @ApiModelProperty(value = "设备分组名称")
    private String groupName;
    
    @Excel(name = "添加时间",width = 30)
    @ApiModelProperty(value = "设备添加时间")
    private String addTime;
    
    @ApiModelProperty(value = "区下级地址")
    private String address;
//    不知道干啥用的
//    @ApiModelProperty(value = "")
//    private String alarmMsg;
    
    
    @ApiModelProperty(value = "用户电话")
    private String phone;
    
    @ApiModelProperty(value = "设备是否注册到onenet平台")
    private String register;
    
    @ApiModelProperty(value = "设备额定功率")
    private String power;
    
    @ApiModelProperty(value = "设备限定电压")
    private String voltage;
    
    @ApiModelProperty(value = "设备电能")
    private String kwh;
    @ApiModelProperty(value = "设备上次抄表时间")
    private String LeastCopyTime;

    @ApiModelProperty(value = "图片")
    private List<String> imgs;
    
    private String iccid;

}
