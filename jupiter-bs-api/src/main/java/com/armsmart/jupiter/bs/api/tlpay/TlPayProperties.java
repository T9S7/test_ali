package com.armsmart.jupiter.bs.api.tlpay;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "tl-pay")
public class TlPayProperties {
    private String url;
    private String appid;
    private String secretKey;
    private String privateKeyPath;
    private String tlPublicKey;
    private String pwd;
    private String tgAccount;
    private String vspCusid;
    private String vspOrgid;
    private String vspCusidKj;
    private String industryCode;
    private String industryName;
    private String consumerIp;
    private String callBackUrl;
    private String sellerCashPlat;
    private String buyerCashPlat;
}
