package com.armsmart.jupiter.bs.api.dto.response;

import com.armsmart.jupiter.bs.api.entity.ThingInfo;
import com.armsmart.jupiter.bs.api.entity.UserInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel(description = "_response")
public class KaThingInfoDetail {
    @ApiModelProperty("thingId")
    private Integer thingId;

    @ApiModelProperty("Title")
    private String topTitle;

    @ApiModelProperty("孚贴Title")
    private String ftTitle;

//    @ApiModelProperty("孚贴描述")
//    private String ftDesc;

    @ApiModelProperty("读卡器Title")
    private String dkqTitle;

//    @ApiModelProperty("读卡器描述")
//    private String dkqDesc;

    @ApiModelProperty("低价")
    private Long lowerPrice;

    @ApiModelProperty("高价")
    private Long hivePrice;

    @ApiModelProperty("孚贴信息")
    private ThingInfo ftThing;

    @ApiModelProperty("读卡器信息")
    private ThingInfo dkqThing;

    @ApiModelProperty("用户信息")
    private UserInfo userInfo;

    @ApiModelProperty("上架物品数")
    private Integer putOnCount;

}
