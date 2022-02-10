package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel(description = "_request")
public class CollectPayParam {
    @NotNull(message = "爱较真用户Id")
    @ApiModelProperty(value = "用户id", required = true)
    private String bizOrderNo;

    @ApiModelProperty(value = "非必填，一年前的代收订单必须上送，yyyy-MM-dd 精确到天 ")
    private String bizOrderCreateDate;

    @NotNull(message = "金额，单位：分；部分代付时，可以少于或等于托管代收订单金额")
    @ApiModelProperty(value = "金额", required = true)
    private Long amount;
}
