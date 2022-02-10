package com.armsmart.jupiter.bs.api.constants;

import lombok.Getter;

/**
 * 订单状态
 *
 * @author wei.lin
 **/
@Getter
public enum OrderStatus {
    /**
     * 订单状态（1：待支付；2：待发货；3：已发货；4：已收货；8：交易成功；9：交易失败）
     */
    WAITING_PAY("待支付", 1),
    WAITING_SEND_OUT("待发货", 2),
    SENT_OUT("已发货", 3),
    RECEIVED("已收货", 4),
    SUCCESS("交易成功", 8),
    FAILED("交易失败", 9),
    ;

    String name;

    int code;

    OrderStatus(String name, int code) {
        this.name = name;
        this.code = code;
    }

    public static OrderStatus getByCode(int code) {
        for (OrderStatus value : values()) {
            if (value.getCode() == code) {
                return value;
            }
        }
        return null;
    }
}
