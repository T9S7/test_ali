package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserThingQueryParam extends PageQueryParam {

    @NotNull
    @ApiModelProperty(value = "当前状态(-1非卖展示，1售卖中，99私密)", required = true)
    private Integer currentState;
    @NotNull
    @ApiModelProperty(value = "用户ID")
    private Integer userId;
}
