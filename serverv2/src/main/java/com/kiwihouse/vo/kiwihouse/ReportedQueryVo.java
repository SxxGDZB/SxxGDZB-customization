package com.kiwihouse.vo.kiwihouse;


import com.kiwihouse.common.annotation.date.Date;
import com.kiwihouse.common.bean.Basic;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @author yjzn
 * @date 2020-01-03-下午 3:28
 */
@Accessors(chain = true)
@ToString
@Getter
@Setter
@ApiModel(description = "查询火警上报信息参数")
public class ReportedQueryVo extends Basic {

    @ApiModelProperty(value = "设备序列号", name = "eqptSn", required = false)
    private String eqptSn;

    @ApiModelProperty(value = "设备imei", name = "imei", required = false)
    private String imei;

    @ApiModelProperty(value = "上报信息种类(测量数据,告警信息,运行数据)", name = "alarmType", required = true, allowableValues = "1,2,3")
    private String alarmType;

    @Date
    @ApiModelProperty(value = "开始时间", name = "startTime", required = false)
    private String startTime;

    @Date
    @ApiModelProperty(value = "结束时间", name = "endTime", required = false)
    private String endTime;

    @ApiModelProperty(value = "排序参数(alarmType=1时生效)", name = "orderBy", required = false, allowableValues = "addTime,csq,cur,cur,vol,pwr,kwh,line_temp,pwr_fct,leak_cur")
    private String orderBy;

    @ApiModelProperty(value = "顺序(alarmType=1时生效)(升序:ascending,倒叙:descending)", name = "sequence", required = false, allowableValues = "ascending,descending")
    private String sequence;

//    @Min(value = 1, message = "page is more than 1")
    @ApiModelProperty(value = "页码", name = "page", required = true, example = "1")
    private int page;

//    @Min(value = 1, message = "limit is more than 1")
    @ApiModelProperty(value = "每页条数", name = "limit", required = true, example = "10")
    private int limit;

    @ApiModelProperty(hidden = true)
    private String userId;
    @ApiModelProperty(value = "设备类型", name = "eqptType")
    private String eqptType;
    
    private String type;
    
}
