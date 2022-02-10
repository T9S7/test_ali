package com.armsmart.jupiter.bs.api.entity;
import lombok.Data;


/**
 *WechatUnifiedOrderInfo entity
 * @author 苏礼刚
 **/
@Data
public class WechatUnifiedOrderInfo{
    /**
    *id
    */
    private Integer id;

    /**
    *业务订单号
    */
    private String orderSn;

    /**
    *应用id
    */
    private String appId;

    /**
    *商户id
    */
    private String partnerId;

    /**
    *预支付交易会话ID
    */
    private String prepayId;

    /**
    *扩展字段
    */
    private String packageStr;

    /**
    *随机字符串
    */
    private String nonceStr;

    /**
    *时间戳
    */
    private String timeStamp;

    /**
    *签名
    */
    private String sign;

    /**
    *创建时间
    */
    private Long createTime;

    /**
    *修改时间
    */
    private Long updateTime;

    /**
    *是否删除
    */
    private Boolean isDel;


}




