package com.armsmart.jupiter.bs.api.constants;

import lombok.Getter;

@Getter
public enum BidStatesType {
    /**
     * (0--出局，1--成交请支付，2--领先，3--出局继续出价)
     */
    OUT("出局", 0),
    DONE_TO_PAY("成交请支付", 1),
    LEAD("领先", 2),
    OUT_CONTINUE("出局继续出价", 3),
    ;

    String name;

    int code;

    BidStatesType(String name, int code) {
        this.name = name;
        this.code = code;
    }

    public static BidStatesType getByCode(int code) {
        for (BidStatesType value : values()) {
            if (value.getCode() == code) {
                return value;
            }
        }
        return null;
    }
}
