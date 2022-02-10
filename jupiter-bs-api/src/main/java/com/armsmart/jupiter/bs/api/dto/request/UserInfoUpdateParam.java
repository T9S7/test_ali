package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;


import javax.validation.constraints.NotNull;

/**
 * UserInfo修改DTO
 *
 * @author wei.lin
 **/
@Data
@ApiModel(description = "_request")
public class UserInfoUpdateParam {


    @NotNull(message = "用户ID不能为空")
    @ApiModelProperty(value = "用户ID", required = true)
    private Integer id;

    @Length(max = 16, message = "昵称长度不能超过16")
    @ApiModelProperty("昵称")
    private String nickname;

    @Length(max = 255, message = "头像长度不能超过255")
    @ApiModelProperty("头像")
    private String avatar;

    @Length(max = 16, message = "真实姓名长度不能超过16")
    @ApiModelProperty("真实姓名")
    private String realName;

    @Length(max = 64, message = "邮箱长度不能超过64")
    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("终端平台：0:安卓；1：IOS")
    private Integer platform;

    @ApiModelProperty("用户类型 0：普通；1：鉴定人")
    private Integer userType;

    @ApiModelProperty("用户描述")
    private String authDesc;
}



