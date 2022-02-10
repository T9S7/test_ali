package com.armsmart.jupiter.bs.api.dto.response;

import com.aliyuncs.auth.sts.AssumeRoleResponse;
import lombok.Data;

/**
 * OSS授权结果
 *
 * @author wei.lin
 **/
@Data
public class AssumeRoleResult {

    private AssumeRoleResponse.Credentials credentials;
    private String region;
    private String bucketName;
}
