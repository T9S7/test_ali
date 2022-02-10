package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel(description = "_request")
public class ThingInfoByIdParam {
    @NotNull(message = "用户ID不能为空")
    @ApiModelProperty(value = "用户ID",required = true)
    private Integer userId;

    @NotNull(message = "物品ID不能为空")
    @ApiModelProperty(value = "物品Id",required = true)
    private Long Id;
}
