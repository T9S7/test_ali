package com.armsmart.jupiter.bs.api.entity;
import lombok.Data;


/**
 *AccountCashDetail entity
 * @author 苏礼刚
 **/
@Data
public class AccountCashDetail{
    /**
    *主键
    */
    private Integer id;

    /**
    *账户id
    */
    private Integer accountId;

    /**
    *保证金状态(1.代收未代付；2.已代付；3.已退还；4.转账(平台账户转出))
    */
    private Integer tradeType;

    /**
    *收款方
    */
    private String payee;

    /**
    *交易时间
    */
    private java.util.Date tradeTime;

    /**
    *交易订单号
    */
    private Long tradeOrderNo;

    /**
    *交易前金额
    */
    private Long preAccountBalance;

    /**
    *交易金额
    */
    private Long tradeMoney;

    /**
    *交易后保证金余额
    */
    private Long cashAccountBalance;

    /**
    *拍卖id
    */
    private Integer sellId;

    /**
    *交易时间
    */
    private Long createTime;

    /**
    *是否删除
    */
    private Boolean isDel;


}




