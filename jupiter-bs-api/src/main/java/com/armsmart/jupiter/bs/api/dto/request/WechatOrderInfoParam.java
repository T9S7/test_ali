package com.armsmart.jupiter.bs.api.dto.request;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel(description = "_request")
public class WechatOrderInfoParam {

    @NotNull(message = "应用APPID不能为空")
    @ApiModelProperty(value = "应用APPID", required = true)
    private String appid;

    @NotNull(message = "商户号不能为空")
    @ApiModelProperty(value = "商户号", required = true)
    private String mch_id;


    @ApiModelProperty(value = "微信订单号", required = false)
    private String transaction_id;

    @NotNull(message = "商户订单id不能为空")
    @ApiModelProperty(value = "商户订单id", required = true)
    private String order_id;

    @ApiModelProperty(value = "商户订单号", required = false)
    private String out_trade_no;

    @ApiModelProperty(value = "随机字符串", required = false)
    private String nonce_str;

    @ApiModelProperty(value = "签名", required = false)
    private String sign;
}
