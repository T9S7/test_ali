package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * SysUser修改DTO
 *
 * @author wei.lin
 **/
@Data
@ApiModel(description = "_request")
public class SysUserUpdateParam {


    @NotNull(message = "用户ID不能为空")
    @ApiModelProperty(value = "用户ID", required = true)
    private Integer userId;

    @Length(max = 16, message = "联系电话长度不能超过16")
    @ApiModelProperty("联系电话")
    private String telephone;

    @Length(max = 64, message = "邮箱长度不能超过64")
    @ApiModelProperty("邮箱")
    private String email;

    @NotBlank(message = "真实姓名不能为空")
    @Length(max = 32, message = "真实姓名长度不能超过32")
    @ApiModelProperty(value = "真实姓名")
    private String realName;

    @ApiModelProperty("性别（1：男；2女：0：未知）")
    private Integer gender;


    @NotNull(message = "角色ID不能为空")
    @ApiModelProperty(value = "角色ID", required = true)
    private Integer roleId;


}



