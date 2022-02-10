package com.armsmart.jupiter.bs.api.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "_response")
public class UserFansCountDetail {
    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("关注数")
    private Integer focusCount;

    @ApiModelProperty("粉丝数")
    private Integer fansCount;
}
