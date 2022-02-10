package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * ProductInfo添加DTO
 * @author 林伟
 **/
@Data
@ApiModel(description = "_request")
public class PayBySMSParam {
    @NotBlank(message="用户id不能为空")
    @ApiModelProperty(value = "用户id",required = true)
    private Integer userId;

    @NotBlank(message="交易订单号")
    @ApiModelProperty(value = "交易订单号",required = true)
    private String bizOrderNo;

    @NotBlank(message="短信验证码")
    @ApiModelProperty(value = "短信验证码",required = true)
    private String verificationCode;

}
