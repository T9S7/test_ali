package com.armsmart.jupiter.bs.api.entity;

import io.swagger.models.auth.In;
import lombok.Data;


/**
 * UserInfo entity
 *
 * @author wei.lin
 **/
@Data
public class UserInfo {
    /**
     * 用户ID
     */
    private Integer id;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 注册时间
     */
    private Long registerTime;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 启用状态
     */
    private Boolean isEnable;

    /**
     * 消息推送注册ID
     */
    private String registrationId;
    /**
     * 终端平台：0:安卓；1：IOS
     */
    private Integer platform;
    /**
     * 用户类型 0：普通；1：鉴定人
     */
    private Integer userType;
    /**
     * 开户状态（0：未开户；1：开户中未绑定手机号；2：已完成开户）
     */
    private Integer accountRegisterStatus;

    /**
     * 账户保证金（分）
     */
    private Long deposit;

    /**
     * 修改时间
     */
    private Long updateTime;

    /**
     * 是否删除
     */
    private Boolean isDel;

    /**
     * 资质认证状态(0-未提交，1-已通过，2-已提交待审核，3-已审核未通过)
     */
    private Integer checkStatus;
    /**
     * 鉴权码
     */
    private String authCode;
    /**
     *用户首页备注
     */
    private String authDesc;

    /**
     * 通联userId
     */
    private String tlUserId;
    /**
     *通联电子协议编号
     */
    private String contractNo;
    /**
     * 是否设置通联支付密码
     */
    private Boolean isSetPwd;

}




