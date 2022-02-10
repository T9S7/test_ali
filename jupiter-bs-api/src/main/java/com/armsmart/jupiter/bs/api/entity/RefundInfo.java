package com.armsmart.jupiter.bs.api.entity;
import lombok.Data;


/**
 *RefundInfo entity
 * @author 苏礼刚
 **/
@Data
public class RefundInfo{
    /**
    *id
    */
    private Integer id;

    /**
    *原始订单号
    */
    private String orderId;

    /**
    *会员号
    */
    private Integer userId;

    /**
     * 支付方式
     */
    private Integer payMatch;

    /**
    *订单金额
    */
    private Long amount;

    /**
    *退款订单状态（0-待退款；1-退款成功；2-退款失败）
    */
    private Integer orderStatus;

    /**
    *createTime
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




