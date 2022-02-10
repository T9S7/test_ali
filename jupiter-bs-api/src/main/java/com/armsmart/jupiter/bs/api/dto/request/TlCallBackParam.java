package com.armsmart.jupiter.bs.api.dto.request;

import cn.hutool.json.JSON;
import lombok.Data;

import java.util.Date;

@Data
public class TlCallBackParam {
    private String notifyTime;
    private String notifyType;
    private String notifyId;
    private String charset;
    private String version;
    private String signType;
    private String sign;
    private String appId;
    private String bizContent;
}
