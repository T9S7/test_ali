package com.armsmart.jupiter.bs.api.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * BidInfo详情DTO
 *
 * @author wei.lin
 **/
@Data
@ApiModel(description = "_response")
public class BidInfoDetail {
    @ApiModelProperty("竞标ID")
    private Long bidId;

    @ApiModelProperty("竞标价格（分）")
    private Long bidPrice;

    @ApiModelProperty("拍卖编号")
    private String sellId;

    @ApiModelProperty("竞拍人ID")
    private Integer userId;

    @ApiModelProperty("竞拍人昵称")
    private String nickname;

    @ApiModelProperty("竞标时间")
    private Long bidTime;

    @ApiModelProperty("是否删除")
    private Boolean isDel;

    @ApiModelProperty("状态 0--出局，1--成交请支付，2--领先，3--出局继续出价")
    private Integer bidStates;

    @ApiModelProperty("下订单截止时间")
    private Long orderDeadline;
}



