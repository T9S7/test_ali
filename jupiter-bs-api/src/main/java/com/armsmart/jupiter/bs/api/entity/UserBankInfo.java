package com.armsmart.jupiter.bs.api.entity;
import lombok.Data;


/**
 *UserBankInfo entity
 * @author 苏礼刚
 **/
@Data
public class UserBankInfo{
    /**
    *序号
    */
    private Integer id;

    /**
    *爱较真用户userId
    */
    private String userId;

    /**
    *银行别名
    */
    private String bankName;

    /**
    *银行代码
    */
    private String bankCode;

    /**
    *银行行号
    */
    private String cardNo;

    /**
    *绑定手机号
    */
    private String phone;

    /**
    *银行卡对应身份证用户姓名
    */
    private String name;

    /**
    *绑卡方式 默认是7
    */
    private Long cardCheck;

    /**
    *身份证号码
    */
    private String identityNo;

    /**
    *有效期
    */
    private String validate;

    /**
    *cvv2
    */
    private String cvv2;

    /**
    *支付行号
    */
    private String unionBank;

    /**
    *流水号
    */
    private String tranceNum;

    /**
    *申请时间
    */
    private String transDate;

    /**
    *银行卡类型(1储蓄卡 2信用卡)
    */
    private String cardType;

    /**
    *签约协议号
    */
    private String agreementNo;

    /**
    *创建时间
    */
    private Long createTime;

    /**
    *是否删除
    */
    private Boolean isDel;


}




