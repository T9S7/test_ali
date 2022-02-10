package com.armsmart.jupiter.bs.api.constants;

import lombok.Getter;

/**
 * 物品多媒体类型
 *
 * @author wei.lin
 **/
@Getter
public enum ThingMediaType {
    /**
     * 物品多媒体类型
     */
    PIC("图片", 0),
    VIDEO("视频", 1),
    ;

    String name;

    int code;

    ThingMediaType(String name, int code) {
        this.name = name;
        this.code = code;
    }

    public static ThingMediaType getByCode(int code) {
        for (ThingMediaType value : values()) {
            if (value.getCode() == code) {
                return value;
            }
        }
        return null;
    }
}
