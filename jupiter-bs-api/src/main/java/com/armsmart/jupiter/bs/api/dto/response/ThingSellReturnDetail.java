package com.armsmart.jupiter.bs.api.dto.response;

import com.armsmart.jupiter.bs.api.entity.ThingInfo;
import com.armsmart.jupiter.bs.api.entity.ThingSellInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ThingSellReturnDetail {
    @ApiModelProperty("物品信息")
    private ThingInfo thingInfo;

    @ApiModelProperty("物品售卖信息")
    private ThingSellInfo thingSellInfo;
}
