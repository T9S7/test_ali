package com.armsmart.jupiter.bs.api.entity;
import lombok.Data;


/**
 *SysUserAuth entity
 * @author 苏礼刚
 **/
@Data
public class SysUserAuth{
    /**
    *主键ID
    */
    private Integer id;

    /**
    *用户ID
    */
    private Integer userId;

    /**
    *登录类型（1：手机、2：账号、3：邮箱）
    */
    private Integer identityType;

    /**
    *身份标识
    */
    private String identifier;

    /**
    *密码凭证
    */
    private String credential;

    /**
    *是否删除
    */
    private Boolean isDel;

    /**
    *创建时间
    */
    private Long createTime;

    /**
    *修改时间
    */
    private Long updateTime;


}




