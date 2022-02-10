package com.armsmart.jupiter.bs.api.error;

import com.armsmart.common.api.ErrorCode;


/**
 * 拍卖响应码定义
 *
 * @author wei.lin
 */
public enum AuctionError implements ErrorCode {

    AUCTION_ITEM_NOT_EXIST(40001, "未查询到相关拍卖信息"),
    SETTING_AUCTION_FORBIDDEN(40002, "当前状态不允许设置拍卖信息"),
    DEPOSIT_PAID(40003, "保证金已支付，请勿重复支付"),
    DEPOSIT_PAY_FORBIDDEN(40004, "当前状态不允许支付保证金"),
    AUCTION_TIME_INVALID(40005, "拍卖时间无效"),
    AUCTION_AUDITED(40006, "送拍单已审核，请勿重复审核"),
    AUCTION_SENT(40007, "已送拍，请勿重复送拍"),
    BID_PRICE_IS_LOW(40008, "竞拍价格必须高于当前最高价"),
    TRANSACTION_IS_NOT_EXIST(40010, "交易信息不存在"),
    BID_ADD_FAILED(40011, "竞拍失败，请重试"),
    NOT_SUFFICIENT_DEPOSIT(40012, "竞拍失败，账户保证金不足"),
    AUCTION_IS_NOT_CLOSE(40013, "当前拍卖非结标状态"),
    BUYER_NOT_PAY(40014, "拍卖物品尚未支付"),
    BUYER_NOT_INPUT_ADDR(40015, "买家未填写收货信息"),
    SELLER_NOT_POST(40016, "卖家尚未邮寄，请勿确认收货"),
    USER_IS_NOT_WINNER(40016, "当前用户非中标人"),
    USER_IS_NOT_OWNER(40017, "当前用户非物品所有人"),
    OWNER_CAN_NOT_BID(40018, "卖家不能参与竞拍"),
    ORDER_ADD_FAILED(40019,"下单失败，请重试"),
    AUCTION_START_TIME_MUST_GT_CURRENT_TIME(40020, "拍卖开始时间必须大于当前时间"),
    AUCTION_START_TIME_MUST_LT_END_TIME(40021, "拍卖开始时间必须小于结束时间"),
    AUCTION_END_TIME_MUST_GT_START_TIME_ONE_HOUR(40022, "拍卖结束时间与开始时间至少间隔1小时"),
    THING_ITEM_NOT_EXIST(40023,"当前物品不存在"),
    USER_HAVING_THING_SELLING(40024, "当前用户存在在售物品，不能销户"),
    USER_NO_CHANNEL(40025,"当前用户不允许销户"),
    USER_HAVING_ORDER_INFO_NO_END(40026,"当前用户存在未完成订单，不能销户"),
    BID_USER_LEAD(40027,"出价已领先"),
    ;


    int code;
    String msg;

    AuctionError(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }


    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.msg;
    }
}
