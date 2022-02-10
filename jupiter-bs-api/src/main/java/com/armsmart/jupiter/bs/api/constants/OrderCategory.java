package com.armsmart.jupiter.bs.api.constants;

import lombok.Getter;

/**
 * 订单类别(1：普通物品；2：上链工具)
 *
 * @author wei.lin
 **/
@Getter
public enum OrderCategory {
    /**
     * 订单类别(1：普通物品；2：上链工具)
     */
    NORMAL("普通物品", 1),
    UPLOAD_TOOL("上链工具", 2),
    ;

    String name;

    int code;

    OrderCategory(String name, int code) {
        this.name = name;
        this.code = code;
    }

    public static OrderCategory getByCode(int code) {
        for (OrderCategory value : values()) {
            if (value.getCode() == code) {
                return value;
            }
        }
        return null;
    }
}
