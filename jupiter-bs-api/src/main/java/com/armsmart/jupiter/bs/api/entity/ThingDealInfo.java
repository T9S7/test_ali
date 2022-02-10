package com.armsmart.jupiter.bs.api.entity;
import lombok.Data;


/**
 *ThingDealInfo entity
 * @author 苏礼刚
 **/
@Data
public class ThingDealInfo{
    /**
    *主键
    */
    private Integer id;

    /**
    *物品id
    */
    private Long thingId;

    /**
    *买家id
    */
    private Long giveUserId;

    /**
    *买家id
    */
    private Long buyUserId;

    /**
    *1 赠送，2 交易
    */
    private Integer type;

    /**
    *当前物品合约地址
    */
    private String contractAddr;

    /**
    *交易合约地址
    */
    private String dealContractAddr;

    /**
    *是否完成
    */
    private Boolean isDone;

    /**
    *完成时间
    */
    private Long doneTime;

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




