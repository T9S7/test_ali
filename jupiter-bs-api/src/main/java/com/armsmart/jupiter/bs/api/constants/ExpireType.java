package com.armsmart.jupiter.bs.api.constants;

import lombok.Getter;

/**
 * redis key过期类型
 *
 * @author wei.lin
 **/
@Getter
public enum ExpireType {
    /**
     * key过期类型 1 支付；2 发货
     */
    PAY("支付", 1),
    SHIPPED("发货", 2),
    ;

    String name;

    int code;

    ExpireType(String name, int code) {
        this.name = name;
        this.code = code;
    }

    public static ExpireType getByCode(int code) {
        for (ExpireType value : values()) {
            if (value.getCode() == code) {
                return value;
            }
        }
        return null;
    }
}
