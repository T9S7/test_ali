package com.armsmart.jupiter.bs.api.entity;

import lombok.Data;
import lombok.Getter;


/**
 * SmsInfo entity
 *
 * @author wei.lin
 **/
@Data
public class SmsInfo {
    /**
     * 主键ID
     */
    private Integer id;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 短信类型（1：注册；2：身份认证）
     */
    private Integer smsType;

    /**
     * 验证码
     */
    private String verifyCode;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 过期时间
     */
    private Long expireTime;

    /**
     * 是否删除
     */
    private Boolean isDel;

    @Getter
    public enum SmsType {
        REG_LOGIN("注册登录", 1),
        AUTH("身份认证", 2);

        String name;

        int code;

        SmsType(String name, int code) {
            this.name = name;
            this.code = code;
        }

    }
}




