package com.armsmart.jupiter.bs.api.dto.request;

import cn.hutool.json.JSONObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel(value = "_request")
public class DepositApplyParam {
//    @NotBlank(message = "订单编号不能为空")
//    @ApiModelProperty(value = "订单编号", required = true)
//    private String	bizOrderNo;

    @NotBlank(message = "爱较真的用户id")
    @ApiModelProperty(value = "爱较真的用户id", required = true)
    private String	bizUserId;

//    @NotBlank(message = "账户集编号")
//    @ApiModelProperty(value = "账户集编号", required = true)
//    private String	accountSetNo;

    @NotBlank(message = "订单金额")
    @ApiModelProperty(value = "订单金额 分", required = true)
    private Long	amount;

    @NotBlank(message = "手续费")
    @ApiModelProperty(value = "手续费", required = true)
    private Long	fee;

//    @NotBlank(message = "交易验证方式")
//    @ApiModelProperty(value = "交易验证方式（0，无验证；1 短信验证（不填默认）,2.密码验证）", required = true)
//    private Long	validateType;

//    @NotBlank(message = "前台通知地址")
//    @ApiModelProperty(value = "前台通知地址", required = true)
//    private String	frontUrl;

//    @NotBlank(message = "后台通知地址")
//    @ApiModelProperty(value = "后台通知地址", required = true)
//    private String	backUrl;

    @ApiModelProperty(value = "订单过期时间")
    private String	orderExpireDatetime;

    @NotBlank(message = "支付方式不能为空")
    @ApiModelProperty(value = "支付方式 1 微信，2 支付宝，3 余额", required = true)
    private Integer payMethod;

    @ApiModelProperty(value = "微信支付入参")
    private TlWxPayParam tlWxPayParam;

    @ApiModelProperty(value = "商品名称")
    private String	goodsName;

    private String bankCardNo;
}

