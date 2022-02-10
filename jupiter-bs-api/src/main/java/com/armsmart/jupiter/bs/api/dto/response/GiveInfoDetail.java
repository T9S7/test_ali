package com.armsmart.jupiter.bs.api.dto.response;

import com.armsmart.jupiter.bs.api.entity.ThingInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "_response")
public class GiveInfoDetail {
    @ApiModelProperty("赠送好友昵称")
    private String nickname;

    @ApiModelProperty("赠送好友头像")
    private String userUrl;

    @ApiModelProperty("赠送物品信息")
    private ThingInfo thingInfo;

}
