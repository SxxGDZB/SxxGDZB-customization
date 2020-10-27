package com.kiwihouse.dao.entity.vx.dev;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@ToString
@Setter
@Getter
public class Add {
	private Integer eqptId;
    /**
     * 设备名称
     */
	@ApiModelProperty(value = "设备名称", name = "eqptName", required = true)
	private String eqptName;
    /**
     * imei号
     */
	@ApiModelProperty(value = "IMEI号", name = "imei", required = true)
	private String imei;
    /**
     * imsi号
     */
	@ApiModelProperty(value = "IMSI号", name = "imsi", required = false)
	private String imsi;
    /**
     * 设备序列号
     */
	@ApiModelProperty(value = "设备序列号（流水号）", name = "eqptSn", required = true)
	private String eqptSn;
    /**
     * 设备类型0、单相 1、三相
     */
	@ApiModelProperty(value = "设备类型(0、单相 1、三相)", name = "eqptType", required = true)
	private String eqptType;
    /**
     * 是否注册到onenet平台
     */
	@ApiModelProperty(value = "是否同时注册到onenet平台(0-不注册，1-注册)", name = "register", required = true)
	private Integer register;
    /**
     * 设备详细地址
     */
	@ApiModelProperty(value = "地址", name = "address", required = false)
	private String eqptAddr;
    /**
     * 纬度
     */
	@ApiModelProperty(value = "纬度", name = "latitude", required = false)
	private String latitude;
    /**
     * 经度
     */
	@ApiModelProperty(value = "经度", name = "longitude", required = false)
	private String longitude;
    /**
     * 用户ID
     */
	@ApiModelProperty(value = "用户ID", name = "userId", required = false)
	private Integer userId;
    /**
     * 所属角色ID
     */
	private Integer roleId;
    /**
     * 省
     */
	@ApiModelProperty(value = "省", name = "province", required = false)
	private String province;
    /**
     * 市
     */
	@ApiModelProperty(value = "市", name = "province", required = false)
	private String city;
    /**
     * 区
     */
	@ApiModelProperty(value = "区", name = "province", required = false)
	private String district;
	
}
