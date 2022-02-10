package com.armsmart.jupiter.bs.api.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ThingUserIdStateDetail {
    @ApiModelProperty("关注状态 -1 未关注,0 本人,1 已关注")
    private Integer focusState;

    @ApiModelProperty("收藏状态 -1 未收藏,0 本人的物品,1 已收藏")
    private Integer CollectState;
}
