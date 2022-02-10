package com.armsmart.jupiter.bs.api.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "_response")
public class ThingListHideResult extends ThingSelectListResult {
    @ApiModelProperty("物品创建时间")
    private Long createTime;
}
