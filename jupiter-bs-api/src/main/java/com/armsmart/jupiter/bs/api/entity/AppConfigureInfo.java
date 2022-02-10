package com.armsmart.jupiter.bs.api.entity;
import lombok.Data;


/**
 *AppConfigureInfo entity
 * @author 苏礼刚
 **/
@Data
public class AppConfigureInfo{
    /**
    *序号
    */
    private Integer id;

    /**
    *app版本
    */
    private String appVersion;

    /**
    *联系电话
    */
    private String telephone;

    /**
    *公众号
    */
    private String offAccount;

    /**
    *用户协议
    */
    private String userAgreement;

    /**
    *隐私政策
    */
    private String privacyPolicy;

    /**
    *预留1
    */
    private String cloume1;

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




