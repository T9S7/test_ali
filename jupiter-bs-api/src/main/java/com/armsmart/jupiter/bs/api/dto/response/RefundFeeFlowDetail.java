package com.armsmart.jupiter.bs.api.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "_response")
public class RefundFeeFlowDetail {
    @ApiModelProperty("日期")
    private String dateTime;

    @ApiModelProperty("微信和支付宝流水")
    private Long vspCusFlow;

    @ApiModelProperty("非微信和支付宝流水")
    private Long vspOrgFlow;

    @ApiModelProperty("微信和支付宝退款总额")
    private Long vspCusReturn;

    @ApiModelProperty("非微信和支付宝退款总额")
    private Long vspOrgReturn;


}
