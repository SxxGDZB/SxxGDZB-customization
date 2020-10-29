package com.kiwihouse.vo.kiwihouse;
import java.util.List;
import com.kiwihouse.common.annotation.doubleNumber.DoubleNumber;
import com.kiwihouse.common.annotation.imei.Imei;
import com.kiwihouse.common.annotation.naturalNumber.NaturalNumber;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author yjzn
 * @date 2020-01-03-下午 7:16
 */
@ToString
@Getter
@Setter
@ApiModel(description = "录入设备信息")
public class EqptAddVo {

    @ApiModelProperty(hidden = true)
    private String eqptId;

    @ApiModelProperty(value = "设备名称", name = "eqptName", required = true)
    private String eqptName;

    @Imei
    @ApiModelProperty(value = "IMEI号", name = "imei", required = true)
    private String imei;

    @ApiModelProperty(value = "IMSI号", name = "imsi", required = false)
    private String imsi;

    @ApiModelProperty(value = "是否同时注册到onenet平台(0-不注册，1-注册)", name = "register", required = true)
    private String register;

    @ApiModelProperty(value = "设备序列号（流水号）", name = "eqptSn", required = true)
    private String eqptSn;

    @NaturalNumber(message = "eqptType is natural number")
    @ApiModelProperty(value = "设备类型(0-单相，1-烟感，2-三相)", name = "eqptType", required = true)
    private String eqptType;

    @DoubleNumber
    @ApiModelProperty(value = "限定功率", name = "power", required = false)
    private String power;

    @DoubleNumber
    @ApiModelProperty(value = "额定电流", name = "electricity", required = false)
    private String electricity;

    @DoubleNumber
    @ApiModelProperty(value = "额定电压", name = "voltage", required = false)
    private String voltage;

    @NaturalNumber
    @ApiModelProperty(value = "用户ID", name = "userId", required = false)
    private String userId;

    @NaturalNumber
    @ApiModelProperty(value = "设备组ID", name = "groupId", required = false)
    private String groupId;

    @NaturalNumber
    @ApiModelProperty(value = "地址簿地址Id", name = "siteId", required = false)
    private String siteId;

    @NaturalNumber
    @ApiModelProperty(value = "省市区编码", name = "code", required = false)
    private String code;

    @ApiModelProperty(value = "地址", name = "address", required = false)
    private String address;


    @ApiModelProperty(value = "纬度", name = "latitude", required = false)
    private String latitude;

    @ApiModelProperty(value = "经度", name = "longitude", required = false)
    private String longitude;

    @ApiModelProperty(value = "图片路径数组", name = "imgs", required = false)
    private List<String> imgs;

    @ApiModelProperty(value = "执行操作的角色ID", name = "doroleId", hidden = true)
    private String roleId;
    @ApiModelProperty(hidden = true)
    private String province;
    @ApiModelProperty(hidden = true)
    private String city;
    @ApiModelProperty(hidden = true)
    private String district;
    @ApiModelProperty(hidden = true)
    private String addTime;

}
