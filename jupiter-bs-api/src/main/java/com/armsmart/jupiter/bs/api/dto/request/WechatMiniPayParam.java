package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@ApiModel(description = "_request")
public class WechatMiniPayParam {
    @NotBlank(message="登录凭证不能为空")
    @ApiModelProperty(value = "登录凭证",required = true)
    private String code;

    @NotNull(message="订单号不能为空")
    @ApiModelProperty(value = "订单号",required = true)
    private Long orderId;
//
//    @NotNull(message="业务类型不能为空")
//    @ApiModelProperty(value = "业务类型,1-支付保证金；2-支付货款",required = true)
//    private Integer businessType;
}
