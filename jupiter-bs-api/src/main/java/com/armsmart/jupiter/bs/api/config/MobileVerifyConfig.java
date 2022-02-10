package com.armsmart.jupiter.bs.api.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author wei.lin
 * @date 2020/12/18
 */
@Data
@Component
@ConfigurationProperties(prefix = "aliyun.mobile-verify")
public class MobileVerifyConfig {

    private String endpoint;

    private String accessKeyId;

    private String accessKeySecret;

}
