package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel(description = "_request")
public class SysUserLoginParam {

    @NotBlank(message = "身份认证信息不能为空")
    @ApiModelProperty(value = "身份认证信息（手机号/账号/邮箱）", required = true)
    private String identifier;

    @NotBlank(message = "密码不能为空")
    @ApiModelProperty(value = "密码", required = true)
    private String password;
}
