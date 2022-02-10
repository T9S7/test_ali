package com.armsmart.jupiter.bs.api.dto.response;

import com.armsmart.jupiter.bs.api.entity.ThingPicInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(description = "_response")
public class ThingSelectListResult {
    @ApiModelProperty("物品id")
    private Long thingId;

    @ApiModelProperty("物品名称")
    private String artName;

    @ApiModelProperty("图片地址")
    private List<ThingPicInfo> pics;

    @ApiModelProperty("发布类型 1 一口价；2 拍卖")
    private Integer sellType;

    @ApiModelProperty("用户ID")
    private Integer userId;

    @ApiModelProperty("拍卖状态 0 拍卖前  1 拍卖中")
    private Integer auctionState;

    @ApiModelProperty("拍卖结束时间")
    private Long auctionTime;

    @ApiModelProperty("当前价格")
    private Long currentPrice;

}
