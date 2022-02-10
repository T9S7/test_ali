package com.armsmart.jupiter.bs.api.entity;
import lombok.Data;


/**
 *OrderInfo entity
 * @author wei.lin
 **/
@Data
public class OrderInfo{
    /**
    *订单ID
    */
    private Long orderId;

    /**
     *订单编号
     */
    private String orderSn;

    /**
    *订单类别(1：普通物品；2：上链工具)
    */
    private Integer orderCategory;

    /**
    *产品ID
    */
    private Long thingId;

    /**
     *产品名称
     */
    private String thingName;

    /**
    *拍卖ID
    */
    private Long thingSellId;

    /**
    *价格（单位：分）
    */
    private Long price;

    /**
     * 物品数量
     */
    private Integer amount;
    /**
    *保险（单位：分）
    */
    private Long insurance;

    /**
    *买家收货地址
    */
    private String buyerAddress;

    /**
    *买家姓名
    */
    private String buyerName;

    /**
    *买家联系电话
    */
    private String buyerMobile;

    /**
    *订单状态（1：待支付；2：待发货；3：已发货；4：已收货；8：交易成功；9：交易失败）
    */
    private Integer orderStatus;

    /**
    *卖家用户ID
    */
    private Integer sellerId;

    /**
    *买家用户ID
    */
    private Integer buyerId;

    /**
    *快递单号
    */
    private String expressNumber;

    /**
    *快递公司
    */
    private String expressCompany;

    /**
    *支付方式（1：微信；2：支付宝;3：余额；4：银行卡）
    */
    private Integer payType;

    /**
     *支付状态 0.未支付 1.支付成功 2.支付失败 默认0
     */
    private Integer payStatus;

    /**
    *支付时间
    */
    private Long payTime;

    /**
    *可支付截止时间
    */
    private Long payDeadline;

    /**
    *发货截止时间
    */
    private Long sendOutDeadline;

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

    /**
     *买家是否删除
     */
    private Boolean buyerDel;

    /**
     *卖家是否删除
     */
    private Boolean sellerDel;

    /**
     * 再次发起请求订单号
     */
    private String tlOrderRequest;
    /**
     *通联订单号
     */
    private String tlOrderSn;

    /**
     *交易类型
     */
    private Integer tradeType;

    /**
     *交易子类型
     */
    private Integer sonTradeType;
    /**
    *交易失败原因-买家
    */
    private String buyerFailureReason;

    /**
     *交易失败原因-卖家
     */
    private String sellerFailureReason;
}




