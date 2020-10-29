package com.kiwihouse.vo.kiwihouse;

import com.kiwihouse.common.annotation.date.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author yjzn
 * @date 2020-2-24 15:46:26
 */
@ToString
@Getter
@Setter
@ApiModel("数据总览查询参数")
public class DataStatisticsVo {

    @Date
    @ApiModelProperty(value = "开始日期",name = "startTime",required = true,example = "2020-01-15")
    private String startTime;
    @Date
    @ApiModelProperty(value = "结束日期",name = "endTime",required = true,example = "2020-01-18")
    private String endTime;
    @ApiModelProperty(hidden = true)
    private String adminId;
    private String imei;
    private Integer roleId;

}
