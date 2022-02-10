package com.armsmart.jupiter.bs.api.entity;
import lombok.Data;


/**
 *UserCompany entity
 * @author 苏礼刚
 **/
@Data
public class UserCompany{
    /**
    *主键
    */
    private Integer id;

    /**
    *用户id
    */
    private Integer userId;

    /**
    *企业id
    */
    private Integer companyId;

    /**
    *用户类型
    */
    private Integer userType;

    /**
    *是否企业官方指定用户
    */
    private Boolean isCompanyOfficial;

    /**
    *createTime
    */
    private Long createTime;

    /**
    *isDel
    */
    private Boolean isDel;


}




