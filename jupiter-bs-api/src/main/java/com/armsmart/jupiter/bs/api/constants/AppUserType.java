package com.armsmart.jupiter.bs.api.constants;

import lombok.Getter;

/**
 * APP用户类型
 *
 * @author wei.lin
 **/
@Getter
public enum AppUserType {
    /**
     * APP用户类型
     */
    NORMAL("普通用户", 0),
    APPRAISER("鉴定师", 1),
    OFFICIAL("官方", 99),
    ;

    String name;

    int code;

    AppUserType(String name, int code) {
        this.name = name;
        this.code = code;
    }

    public static AppUserType getByCode(int code) {
        for (AppUserType value : values()) {
            if (value.getCode() == code) {
                return value;
            }
        }
        return null;
    }
}
