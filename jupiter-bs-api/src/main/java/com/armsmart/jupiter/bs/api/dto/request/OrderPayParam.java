package com.armsmart.jupiter.bs.api.dto.request;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@ApiModel(description = "_request")
public class OrderPayParam {
    @NotNull(message="购买用户不能为空")
    @ApiModelProperty(value = "购买用户id",required = true)
    private String buyerId;

    @NotNull(message="卖家用户不能为空")
    @ApiModelProperty(value = "卖家用户id",required = true)
    private String sellerId;

    @NotNull(message="订单编号不能空")
    @ApiModelProperty(value = "orderId",required = true)
    private String orderId;
//
//    @ApiModelProperty(value = "再次支付订单号")
//    private String orderIdAgain;

    @NotNull(message="支付金额")
    @ApiModelProperty(value = "支付金额（分）",required = true)
    private Long amount;

    @NotNull(message="支付方式")
    @ApiModelProperty(value = "支付方式（1-微信，2-支付宝，3-余额，4-快捷支付）",required = true)
    private Integer payMatch;

    @ApiModelProperty(value = "微信支付入参")
    private TlWxPayParam tlWxPayParam;

//
//    @ApiModelProperty(value = "交易验证方式（0，无，1短信-默认，2-支付密码）")
//    private Long validateType;
//
//    @NotBlank(message="物品id不能为空")
//    @ApiModelProperty(value = "物品id")
//    private Long thing_id;
//
//    @ApiModelProperty(value = "拍卖编号")
//    private String thing_sell_id;

    @ApiModelProperty(value = "快捷支付银行卡号")
    private String bankCardNo;
}
