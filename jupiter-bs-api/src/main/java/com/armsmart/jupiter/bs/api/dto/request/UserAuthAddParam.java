package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * UserAuth添加DTO
 *
 * @author wei.lin
 **/
@Data
@ApiModel(description = "_request")
public class UserAuthAddParam {


    @NotBlank(message = "手机号不能为空")
    @Length(max = 11, message = "手机号长度不能超过11")
    @ApiModelProperty(value = "手机号", required = true)
    private java.lang.String mobile;

    @NotBlank(message = "密码凭证不能为空")
    @Length(max = 64, message = "密码凭证长度不能超过64")
    @ApiModelProperty(value = "密码凭证", required = true)
    private java.lang.String password;

    @NotBlank(message = "验证码不能为空")
    @Length(min = 6,max = 6, message = "请输入6位验证码")
    @ApiModelProperty(value = "验证码", required = true)
    private String verifyCode;
}



