package com.armsmart.jupiter.bs.api.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SysUserLoginResult {

    @ApiModelProperty("用户ID")
    private Integer userId;

    @ApiModelProperty("用户账号")
    private String username;

    @ApiModelProperty("realName")
    private String realName;

    @ApiModelProperty("性别")
    private Integer gender;

    @ApiModelProperty("联系电话")
    private String telephone;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("角色ID")
    private Integer roleId;

    @ApiModelProperty("token")
    private String token;
    
    @ApiModelProperty("tokenHead")
    private String tokenHead;

    @ApiModelProperty("角色详情")
    private SysRoleDetail sysRole;

}
