package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ThingUserIdStateParam {
    @ApiModelProperty("卖家id")
    private Long userId;

    @ApiModelProperty("物品id")
    private Long thingId;
}
