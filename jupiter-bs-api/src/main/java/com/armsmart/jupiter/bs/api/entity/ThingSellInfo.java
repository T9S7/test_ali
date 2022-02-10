package com.armsmart.jupiter.bs.api.entity;
import lombok.Data;


/**
 *ThingSellInfo entity
 * @author 苏礼刚
 **/
@Data
public class ThingSellInfo{
    /**
    *主键ID
    */
    private Long id;

    /**
    *物品ID
    */
    private Long thingId;

    /**
    *发布类型 1 一口价；2 拍卖
    */
    private Integer sellType;

    /**
    *卖家寄语
    */
    private String sellerInfo;

    /**
    *一口价价格
    */
    private Long thingPrice;

    /**
    *拍卖 卖家估价
    */
    private Long sellerPrice;

    /**
    *拍卖 保证金
    */
    private Long margin;

    /**
    *拍卖 起拍价
    */
    private Long sellStartPrice;

    /**
    *拍卖 加价幅度
    */
    private Long sellAddPrice;

    /**
    *拍卖开始时间
    */
    private Long auctionStartTime;

    /**
    *拍卖结束时间
    */
    private Long auctionEndTime;

    /**
     * 上架状态
     */
    private Boolean putOn;

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




