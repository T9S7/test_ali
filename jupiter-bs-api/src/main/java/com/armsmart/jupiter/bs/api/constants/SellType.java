package com.armsmart.jupiter.bs.api.constants;

import lombok.Getter;

/**
 * 发布类型
 *
 * @author wei.lin
 **/
@Getter
public enum SellType {
    /**
     * 发布类型 1 一口价；2 拍卖
     */
    BUYOUT("一口价", 1),
    AUCTION("拍卖", 2),
    ;

    String name;

    int code;

    SellType(String name, int code) {
        this.name = name;
        this.code = code;
    }

    public static SellType getByCode(int code) {
        for (SellType value : values()) {
            if (value.getCode() == code) {
                return value;
            }
        }
        return null;
    }
}
