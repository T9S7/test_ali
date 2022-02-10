package com.armsmart.jupiter.bs.api.constants;

import lombok.Getter;

/**
 * APP用户平台
 *
 * @author wei.lin
 **/
@Getter
public enum PlatformType {
    /**
     * APP用户平台
     */
    android("安卓", 0),
    ios("ios", 1);

    String name;

    int code;

    PlatformType(String name, int code) {
        this.name = name;
        this.code = code;
    }

    public static PlatformType getByCode(int code) {
        for (PlatformType value : values()) {
            if (value.getCode() == code) {
                return value;
            }
        }
        return null;
    }
}
