package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@Data
@ApiModel(description = "_request")
public class AddOrderParam {
    @NotNull(message = "业务类型不能为空")
    @ApiModelProperty(value = "业务类型 1- 卖家支付保证金，2-买家支付保证金，3-支付货款", required = true)
    private Integer businessType;

    @ApiModelProperty(value = "操作用户id 支付保证金必传")
    private String userId;

    @ApiModelProperty(value = "交易金额 支付保证金必传")
    private Long amount;

    @ApiModelProperty(value = "物品id 卖家支付保证金必填")
    private Long thingId;

    @ApiModelProperty(value = "发布物品id 买家家支付保证金必填")
    private Long thingSellId;

    @ApiModelProperty(value = "订单编号 货款支付必传")
    private String orderId;
}
