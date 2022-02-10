package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ThingSellTypeQuery extends PageQueryParam {
    @ApiModelProperty("用户ID")
    private Long userId;

    @ApiModelProperty("类型Id -1 非卖展示  99 私密隐藏")
    private Integer sellType;
}
