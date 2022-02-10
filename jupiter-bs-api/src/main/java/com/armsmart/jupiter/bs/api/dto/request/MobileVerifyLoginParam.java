package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author wei.lin
 * @date 2021/9/8
 */
@Data
public class MobileVerifyLoginParam extends PushAccessParam {

    @NotBlank(message = "AccessToken不能为空")
    @ApiModelProperty(value = "App端SDK获取AccessToken参数值", required = true)
    private String accessToken;
}
