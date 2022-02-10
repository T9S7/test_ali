package com.armsmart.jupiter.bs.api.constants;

import lombok.Getter;

/**
 * APP通知类别
 *
 * @author wei.lin
 **/
@Getter
public enum AppNoticeCategory {
    /**
     * APP通知类别
     */
    MAINTENANCE("维护通知", 0),
    ;

    String name;

    int code;

    AppNoticeCategory(String name, int code) {
        this.name = name;
        this.code = code;
    }

    public static AppNoticeCategory getByCode(int code) {
        for (AppNoticeCategory value : values()) {
            if (value.getCode() == code) {
                return value;
            }
        }
        return null;
    }
}
