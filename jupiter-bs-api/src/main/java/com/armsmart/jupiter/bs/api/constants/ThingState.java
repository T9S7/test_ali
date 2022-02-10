package com.armsmart.jupiter.bs.api.constants;

import lombok.Getter;

/**
 * 物品状态
 *
 * @author wei.lin
 **/
@Getter
public enum ThingState {
    /**
     * (-1， 非卖展示，0未发布，1售卖中，2已出售，3赠送中，9已下架，99私密隐藏)
     */
    NOT_SALE("非卖展示", -1),
    NOT_PUBLISH("0未发布", 0),
    ON_SALE("1售卖中", 1),
    SOLD("2已出售", 2),
    GIVING("3赠送中", 3),
    PUT_OFF("9已下架", 9),
    PRIVATE("99私密隐藏", 99),
    ;

    String name;

    int code;

    ThingState(String name, int code) {
        this.name = name;
        this.code = code;
    }

    public static ThingState getByCode(int code) {
        for (ThingState value : values()) {
            if (value.getCode() == code) {
                return value;
            }
        }
        return null;
    }
}
