package com.armsmart.jupiter.bs.api.entity;

import lombok.Data;


/**
 * BidInfo entity
 *
 * @author wei.lin
 **/
@Data
public class BidInfo {
    /**
     * 竞标ID
     */
    private Long bidId;

    /**
     * 竞标价格（分）
     */
    private Long bidPrice;

    /**
     * 拍卖编号
     */
    private Long sellId;

    /**
     * 竞拍人ID
     */
    private Integer userId;

    /**
     * 竞拍人昵称
     */
    private String nickname;

    /**
     * 竞标时间
     */
    private Long bidTime;

    /**
     * 是否为自动出价
     */
    private Boolean isAuto;

    /**
     * 是否删除
     */
    private Boolean isDel;

    /**
     * 状态 0--出局，1--成交请支付，2--领先，3--出局继续出价
     */
    private Integer bidStates;
    /**
     * 下订单截止时间
     */
    private Long orderDeadline;


}




