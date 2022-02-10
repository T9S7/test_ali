package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 更换手机号DTO
 *
 * @author wei.lin
 **/
@Data
@ApiModel(description = "_request")
public class UserAuthChangePhoneNumParam {

    @NotNull(message = "用户ID不能为空")
    @ApiModelProperty(value = "用户ID", required = true)
    private Integer userId;

    @NotBlank(message = "手机号不能为空")
    @Length(max = 11, message = "手机号长度不能超过11")
    @ApiModelProperty(value = "手机号", required = true)
    private String mobile;

    @NotBlank(message = "验证码不能为空")
    @Length(min = 6, max = 6, message = "请输入6位验证码")
    @ApiModelProperty(value = "验证码", required = true)
    private String verifyCode;
}



