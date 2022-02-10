package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel(description = "_request")
public class BindBankCardParam {

    @NotNull(message = "用户id")
    @ApiModelProperty(value = "用户id", required = true)
    private String userId;

    @NotNull(message = "流水号")
    @ApiModelProperty(value = "流水号", required = true)
    private String tranceNum;

//    @NotNull(message = "申请时间")
//    @ApiModelProperty(value = "申请时间", required = true)
//    private String transDate;

    @NotNull(message = "用户id")
    @ApiModelProperty(value = "用户id", required = true)
    private String phone;

    @ApiModelProperty(value = "有效期")
    private String validate;

    @ApiModelProperty(value = "CVV2")
    private String cvv2;

    @NotNull(message = "短信验证码")
    @ApiModelProperty(value = "短信验证码", required = true)
    private String verificationCode;
}
