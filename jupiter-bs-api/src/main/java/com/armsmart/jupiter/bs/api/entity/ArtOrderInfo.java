package com.armsmart.jupiter.bs.api.entity;
import lombok.Data;


/**
 *ArtOrderInfo entity
 * @author wei.lin
 **/
@Data
public class ArtOrderInfo {
    /**
    *订单ID
    */
    private Long orderId;

    /**
    *店铺艺术品ID
    */
    private Long storeArtId;

    /**
    *艺术品ID
    */
    private Long artId;

    /**
    *价格（单位：分）
    */
    private Long price;

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
    *订单状态（1：待填写收货地址；2：待发货；3：已发货；4：交易成功）
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
    *店铺ID
    */
    private Long storeId;

    /**
    *快递单号
    */
    private String expressNumber;

    /**
    *快递公司
    */
    private String expressCompany;

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




