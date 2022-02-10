package com.armsmart.jupiter.bs.api.entity;
import lombok.Data;


/**
 *CompanyInfo entity
 * @author 苏礼刚
 **/
@Data
public class CompanyInfo{
    /**
    *id
    */
    private Integer id;

    /**
    *企业名称
    */
    private String companyName;

    /**
    *企业描述
    */
    private String companyDesc;

    /**
    *企业类型
    */
    private Integer companyType;

    /**
    *企业头像地址
    */
    private String companyUrl;

    /**
    *是否官方认证
    */
    private Boolean isOfficial;

    /**
    *createTime
    */
    private Long createTime;

    /**
    *updateTime
    */
    private Long updateTime;

    /**
    *isDel
    */
    private Boolean isDel;


}




