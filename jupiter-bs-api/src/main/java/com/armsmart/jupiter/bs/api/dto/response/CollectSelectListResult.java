package com.armsmart.jupiter.bs.api.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "_response")
public class CollectSelectListResult extends ThingSelectListResult {
    @ApiModelProperty("收藏id")
    private Long id;
}
