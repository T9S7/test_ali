package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel(description = "_request")
public class UnbindPhoneParam {
    @NotNull(message = "爱较真用户id不可为空")
    @ApiModelProperty(value = "userId",required = true)
    private String userId;

    @NotNull(message = "手机号不可为空")
    @ApiModelProperty(value = "手机号",required = true)
    private String phone;

    @NotNull(message = "验证码不可为空")
    @ApiModelProperty(value = "验证码",required = true)
    private String verificationCode;
}
