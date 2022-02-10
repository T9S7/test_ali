package com.armsmart.jupiter.bs.api.entity;
import lombok.Data;


/**
 *WechatOrderInfo entity
 * @author 苏礼刚
 **/
@Data
public class WechatOrderInfo{
    /**
    *id
    */
    private Integer id;

    /**
    *微信支付分配的终端设备号
    */
    private String deviceInfo;

    /**
    *用户标识
    */
    private String openid;

    /**
    *用户是否关注公众账号，Y-关注，N-未关注
    */
    private String isSubscribe;

    /**
    *交易类型
    */
    private String tradeType;

    /**
    *交易状态
    */
    private String tradeState;

    /**
    *付款银行
    */
    private String bankType;

    /**
    *总金额
    */
    private Integer totalFee;

    /**
    *货币种类
    */
    private String feeType;

    /**
    *现金支付金额
    */
    private Integer cashFee;

    /**
    *现金支付货币类型
    */
    private String cashFeeType;

    /**
    *应结订单金额
    */
    private Integer settlementTotalFee;

    /**
    *代金券金额
    */
    private Integer couponFee;

    /**
    *代金券使用数量
    */
    private Integer couponCount;

    /**
    *代金券ID
    */
    private String couponIdNo;

    /**
    *代金券类型
    */
    private String couponTypeNo;

    /**
    *单个代金券支付金额
    */
    private String couponFeeNo;

    /**
    *微信支付订单号
    */
    private String transactionId;

    /**
    *商户订单号
    */
    private String outTradeNo;

    /**
    *附加数据
    */
    private String attach;

    /**
    *支付完成时间
    */
    private String timeEnd;

    /**
    *交易状态描述
    */
    private String tradeStateDesc;

    /**
     *支付发生错误时的信息
     */
    private String returnMsg;

    /**
    *创建时间
    */
    private Long createTime;

    /**
    *更新时间
    */
    private Long updateTime;

    /**
    *是否删除
    */
    private Boolean isDel;




}




