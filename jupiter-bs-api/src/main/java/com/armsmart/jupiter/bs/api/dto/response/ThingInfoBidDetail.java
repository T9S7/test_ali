package com.armsmart.jupiter.bs.api.dto.response;

import com.armsmart.jupiter.bs.api.entity.BidInfo;
import com.armsmart.jupiter.bs.api.entity.ThingInfo;
import com.armsmart.jupiter.bs.api.entity.ThingSellInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel(description = "_response")
public class ThingInfoBidDetail {
    @ApiModelProperty("物品信息")
    private ThingInfo thingInfo;
    @ApiModelProperty("发布信息")
    private ThingSellInfo thingSellInfo;
    @ApiModelProperty("拍卖信息")
    private BidInfo bidInfo;
    @ApiModelProperty("拍卖剩余时间（毫秒）0-拍卖未开始，-1--拍卖已结束")
    private Long bidLastTime;
    @ApiModelProperty("状态 0--出局，1--成交请支付，2--领先，3--出局继续出价")
    private Integer bidStates;
    @ApiModelProperty("下订单截止时间")
    private Long orderDeadline;
    @ApiModelProperty("卖家信息")
    private SellerInfoDetail sellerInfo;

}
