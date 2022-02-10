package com.armsmart.jupiter.bs.api.tlpay;

import lombok.Getter;

@Getter
public enum TlNotifyType {
    /**
     * 通联回调通知类型
     */
    SIGNCONTRACT("allinpay.yunst.memberService.signContract") ;


    String notifyType;

    TlNotifyType(String notifyType) {
        this.notifyType = notifyType;
    }
}
