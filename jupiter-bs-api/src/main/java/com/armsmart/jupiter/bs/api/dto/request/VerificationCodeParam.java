package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(description = "_request")
public class VerificationCodeParam {
    private String appSecret;
    private String appUserCode;
    private String mobile;
    private String funcode;
    private String flag;

}
