package com.armsmart.jupiter.bs.api.constants;

import lombok.Getter;

/**
 * 鉴权类型
 *
 * @author wei.lin
 **/
@Getter
public enum IdentityType {
    /**
     * 鉴权类型
     */
    MOBILE("手机", 1),
    USERNAME("账号", 2),
    EMAIL("邮箱", 3);

    String name;

    int code;

    IdentityType(String name, int code) {
        this.name = name;
        this.code = code;
    }
}
