package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
public class SysUserChangePwdParam {

    @NotBlank(message = "用户账号不能为空")
    @Length(max = 32, message = "用户账号长度不能超过32")
    @ApiModelProperty(value = "用户账号", required = true)
    private String username;

    @NotBlank(message = "旧密码不能为空")
    @Length(max = 32, message = "旧密码长度不能超过32")
    @ApiModelProperty(value = "旧密码", required = true)
    private String oldPassword;

    @NotBlank(message = "新密码不能为空")
    @Length(max = 32, message = "新密码长度不能超过32")
    @ApiModelProperty(value = "新密码", required = true)
    private String newPassword;


}
