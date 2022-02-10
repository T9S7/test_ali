package com.armsmart.jupiter.bs.api.entity;
import lombok.Data;


/**
 *AppNotice entity
 * @author wei.lin
 **/
@Data
public class AppNotice{
    /**
    *主键ID
    */
    private Integer id;

    /**
    *通知标题
    */
    private String title;

    /**
    *内容
    */
    private String content;

    /**
    *签名落款
    */
    private String sign;

    /**
    *类型（0：维护通知）
    */
    private Integer category;

    /**
    *是否启用
    */
    private Boolean enabled;

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




