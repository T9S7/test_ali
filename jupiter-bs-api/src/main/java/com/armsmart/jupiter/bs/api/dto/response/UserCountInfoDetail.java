package com.armsmart.jupiter.bs.api.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "_response")
public class UserCountInfoDetail {
    @ApiModelProperty("用户id")
    private Integer userId;

    @ApiModelProperty("参拍数")
    private Integer bidCount;

    @ApiModelProperty("收藏数")
    private Integer collectCount;

    @ApiModelProperty("关注数")
    private Integer focusCount;

    @ApiModelProperty("粉丝数")
    private Integer fansCount;
}
