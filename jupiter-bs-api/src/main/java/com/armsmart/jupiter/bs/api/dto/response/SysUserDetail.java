package com.armsmart.jupiter.bs.api.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * SysUser详情DTO
 *
 * @author wei.lin
 **/
@Data
@ApiModel(description = "_response")
public class SysUserDetail {

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

    @ApiModelProperty("启用状态")
    private Boolean isEnable;

    @ApiModelProperty("角色ID")
    private Integer roleId;

    @ApiModelProperty("创建时间")
    private Long createTime;

    @ApiModelProperty("修改时间")
    private Long updateTime;

    @ApiModelProperty("最后登录时间")
    private Long lastLoginTime;

    /*@ApiModelProperty("公钥模数")
    private String modulus;

    @ApiModelProperty("公钥指数")
    private String exponent;*/

    @ApiModelProperty("用户角色")
    private SysRoleDetail sysRole;


}



