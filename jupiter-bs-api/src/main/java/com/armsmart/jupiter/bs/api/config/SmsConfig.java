package com.armsmart.jupiter.bs.api.config;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author wei.lin
 * @date 2020/12/18
 */
@Data
@Component
@ConfigurationProperties(prefix = "aliyun.sms")
public class SmsConfig {

    private String endpoint;

    private String accessKeyId;

    private String accessKeySecret;

    private String region;

    private String signName;

    private String registerTemplateCode;
    /**
     * 管理员创建APP用户的短信模板
     */
    private String adminRegisterTemplateCode;

    private String authTemplateCode;
    /**
     * 短信验证码有效期：默认600秒
     */
    private int durationTime = 600;
    /**
     * 短信发送间隔时间：默认一分钟只能发送一次
     */
    private int intervalTime = 60;
}
