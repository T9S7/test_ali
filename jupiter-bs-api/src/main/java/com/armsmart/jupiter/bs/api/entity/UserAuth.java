package com.armsmart.jupiter.bs.api.entity;
import lombok.Data;


/**
 *UserAuth entity
 * @author wei.lin
 **/
@Data
public class UserAuth{
    /**
    *主键ID
    */
    private java.lang.Integer id;

    /**
    *用户ID
    */
    private java.lang.Integer userId;

    /**
    *登录类型
    */
    private java.lang.Integer identityType;

    /**
    *标识
    */
    private java.lang.String identifier;

    /**
    *密码凭证
    */
    private java.lang.String credential;

    /**
    *是否删除
    */
    private java.lang.Boolean isDel;


}




