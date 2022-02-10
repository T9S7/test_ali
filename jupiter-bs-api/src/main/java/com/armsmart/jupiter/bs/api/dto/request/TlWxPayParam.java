package com.armsmart.jupiter.bs.api.dto.request;

import lombok.Data;

@Data
public class TlWxPayParam {
//    private String vspCusid;
    private String subAppid;
    private String limitPay;
    private Long amount;
    private String acct;


}
