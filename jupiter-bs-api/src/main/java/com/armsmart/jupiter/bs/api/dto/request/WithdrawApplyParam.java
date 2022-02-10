package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel(description = "_request")
public class WithdrawApplyParam {
    @NotNull(message = "用户id")
    @ApiModelProperty(value = "用户id", required = true)
    private String userId;

    @NotNull(message = "提现金额")
    @ApiModelProperty(value = "提现金额", required = true)
    private Long amount;

//    @NotNull(message = "校验方式")
//    @ApiModelProperty(value = "校验方式", required = true)
//    private Long validateType;

    @NotNull(message = "银行卡号")
    @ApiModelProperty(value = "银行卡号", required = true)
    private String bankCardNo;

}
