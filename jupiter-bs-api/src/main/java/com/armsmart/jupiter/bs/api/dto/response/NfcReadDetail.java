package com.armsmart.jupiter.bs.api.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "_response")
public class NfcReadDetail extends ThingInfoDetail {
    @ApiModelProperty("鉴权用户是否物品所有者 0 否，1 是")
    private Integer isSelf;

    @ApiModelProperty("是否华夏卡 0 否，1 是")
    private Integer isHuaXiaCard;

    @ApiModelProperty("是否确过权，0.未确权 1.已确权 默认0")
    private Integer isChangedRight;
}
