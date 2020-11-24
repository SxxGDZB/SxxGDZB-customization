package com.kiwihouse.vo.kiwihouse;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import com.kiwihouse.common.annotation.imei.Imei;
import com.kiwihouse.common.annotation.naturalNumber.NaturalNumber;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author yjzn
 * @date 2020-1-6 11:53:19
 */
@ToString
@Getter
@Setter
public class CommandVo{

//    @Imei
    @NotBlank(message = "imei is not null")
    private String imei;

    @NaturalNumber
    @NotBlank(message = "eqptType is not null")
    private String eqptType;

    @Valid
    private Register register;


}
