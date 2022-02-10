package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * SysUser 查询参数
 *
 * @author wei.lin
 **/
@Data
@EqualsAndHashCode(callSuper = false)
public class SysUserQueryParam extends PageQueryParam {
    @ApiModelProperty("用户ID")
    private Integer userId;

    @ApiModelProperty("用户账号")
    private String username;

    @ApiModelProperty("姓名")
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

    @ApiModelProperty("角色类型")
    private Integer roleType;

    @ApiModelProperty(value = "是否删除", hidden = true)
    private Boolean isDel;


}




