package com.armsmart.jupiter.bs.api.dto.response;

import com.armsmart.jupiter.bs.api.entity.BidInfo;
import com.armsmart.jupiter.bs.api.entity.ThingInfo;
import com.armsmart.jupiter.bs.api.entity.ThingSellInfo;
import com.armsmart.jupiter.bs.api.entity.UserInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;

import java.util.List;


@Data
@ApiModel(description = "_response")
public class ThingIdInfoDetail {
    @ApiModelProperty("物品信息")
    private ThingInfo thingInfo;
    @ApiModelProperty("发布信息")
    private ThingSellInfo thingSellInfo;
    @ApiModelProperty("拍卖信息")
    private List<BidInfo> bidInfoList;
    @ApiModelProperty("卖家信息")
    private SellerInfoDetail sellerInfo;
    @ApiModelProperty("拍卖剩余时间（毫秒）0-拍卖未开始，-1--拍卖已结束")
    private Long bidLastTime;
//    @ApiModelProperty("关注状态 0 未关注，1 已关注")
//    private Integer bidStates;
    @ApiModelProperty("收藏状态 -1 未收藏,0 本人的物品,1 已收藏")
    private Integer collectState;
    @ApiModelProperty("买家保证金支付状态 0-未支付，1-已支付")
    private Integer marginPayState;
}
