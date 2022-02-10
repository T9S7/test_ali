package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ThingGiveParam extends ModifyOwnerAddInfoParam {

    @NotNull(message = "物品id")
    @ApiModelProperty(value = "物品id",required = true)
    private Long thingId;
//
//    @NotNull(message = "当前用户id")
//    @ApiModelProperty(value = "当前用户id",required = true)
//    private Integer userId;

    @NotNull(message = "赠送用户id")
    @ApiModelProperty(value = "赠送用户id",required = true)
    private Integer giveUserId;

}
