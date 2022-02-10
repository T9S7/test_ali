package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * ProductInfo添加DTO
 * @author 林伟
 **/
@Data
@ApiModel(description = "_request")
public class PayCashParam {
    @NotBlank(message="用户id不能为空")
    @ApiModelProperty(value = "用户id",required = true)
//    private String user_id;
    private Integer userId;

    @NotBlank(message="支付金额")
    @ApiModelProperty(value = "支付金额（分）",required = true)
    private Long amount;

    @NotBlank(message="支付方式")
    @ApiModelProperty(value = "支付方式（1-微信，2-支付宝，3-余额，4-快捷支付）",required = true)
    private Integer payMatch;

    @ApiModelProperty(value = "微信支付入参")
    private TlWxPayParam tlWxPayParam;

    @ApiModelProperty(value = "交易验证方式（0，无，1短信-默认，2-支付密码）")
    private Long validateType;

    @NotBlank(message="物品id不能为空")
    @ApiModelProperty(value = "物品id")
    private Long thingId;

    @ApiModelProperty(value = "拍卖编号")
    private String thingSellId;

    @ApiModelProperty(value = "快捷支付银行卡号")
    private String bankCardNo;
}
