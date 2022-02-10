package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
@Data
@ApiModel(description = "_request")
public class SetRealNameParam {
    @NotNull(message = "爱较真用户Id")
    @ApiModelProperty(value = "用户id", required = true)
    private String userId;

    @NotNull(message = "操作手机号")
    @ApiModelProperty(value = "手机号", required = true)
    private String phone;

    @NotNull(message = "验证码")
    @ApiModelProperty(value = "验证码", required = true)
    private String verificationCode;

    @NotNull(message = "真实姓名")
    @ApiModelProperty(value = "真实姓名", required = true)
    private String name;

    @NotNull(message = "身份证号码")
    @ApiModelProperty(value = "身份证号码", required = true)
    private String identityNo;

}
