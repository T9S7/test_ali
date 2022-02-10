package com.armsmart.jupiter.bs.api.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "_response")
public class MiniPayDetail {
    @ApiModelProperty("是否重复支持")
    private Integer payAgain;

    @ApiModelProperty("通联查询订单编号")
    private String bizOrderNo;

    @ApiModelProperty("小程序入参")
    private Long orderId;
}
