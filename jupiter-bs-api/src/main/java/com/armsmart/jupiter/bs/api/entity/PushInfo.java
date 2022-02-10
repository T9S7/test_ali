package com.armsmart.jupiter.bs.api.entity;
import lombok.Data;


/**
 *PushInfo entity
 * @author 苏礼刚
 **/
@Data
public class PushInfo{
    /**
    *主键ID
    */
    private Integer id;

    /**
    *用户id
    */
    private Integer userId;

    /**
    *手机号
    */
    private String mobile;

    /**
    *平台 0:安卓；1：IOS
    */
    private Integer platform;

    /**
    *消息推送注册ID
    */
    private String registrationId;

    /**
    *消息类型(1.确认签名，2.送评单消息，3.校验消息，4.交易消息，5.系统消息)
    */
    private Integer pushType;

    /**
    *推送消息
    */
    private String pushInfo;

    /**
    *消息的扩展字段
    */
    private String extras;

    /**
    *推送id
    */
    private Integer sendno;

    /**
    *msg Id
    */
    private String msgId;

    /**
    *是否已读（0.未读，1.已读）
    */
    private Boolean isRead;

    /**
    *创建时间
    */
    private Long createTime;

    /**
    *updateTime
    */
    private Long updateTime;

    /**
    *阅读时间
    */
    private Long readTime;

    /**
    *是否删除
    */
    private Boolean isDel;


}




