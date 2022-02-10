package com.armsmart.jupiter.bs.api.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserLoginResult {

    @ApiModelProperty(value = "访问令牌")
    private String token;

    @ApiModelProperty(value = "访问令牌头")
    private String tokenHead;

    @ApiModelProperty(value = "用户基本信息")
    private UserInfoDetail userInfo;


    @ApiModelProperty(value = "用户认证信息")
    private UserAuthenticationResult userAuthenticationResult;

    @ApiModelProperty(value = "用户类型")
    private Integer userType;
}
