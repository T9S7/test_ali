package com.armsmart.jupiter.bs.api.config;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
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
@ConfigurationProperties(prefix = "aliyun.oss")
public class OssConfig {

    private String endpoint;

    private String internalEndpoint;

    private String stsEndpoint;

    private String accessKeyId;

    private String accessKeySecret;
    /**
     * 临时授权有效期
     */
    private Long durationSeconds;

    private String region;

    private String roleArn;
    /**
     * 头像OSS桶名称
     */
    private String avatarBucketName;
    /**
     * app升级包桶名称
     */
    private String appPackageBucketName = "jupiter-app-package";


    @Bean
    public OSS ossClient() {
        return new OSSClientBuilder().build(internalEndpoint, accessKeyId, accessKeySecret);
    }
}
