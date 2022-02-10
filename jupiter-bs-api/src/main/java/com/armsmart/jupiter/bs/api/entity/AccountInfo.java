package com.armsmart.jupiter.bs.api.entity;
import lombok.Data;


/**
 *AccountInfo entity
 * @author 苏礼刚
 **/
@Data
public class AccountInfo{
    /**
    *账户id
    */
    private Integer accountId;

    /**
    *用户id
    */
    private Integer userId;

    /**
    *账户余额
    */
    private Long balance;

    /**
    *冻结资金
    */
    private Long frozenCapital;

    /**
    *保证金金额
    */
    private Long cashDeposit;

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




