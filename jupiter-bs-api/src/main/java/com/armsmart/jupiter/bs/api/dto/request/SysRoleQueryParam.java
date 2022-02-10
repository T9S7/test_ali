package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * SysRole 查询参数
 *
 * @author wei.lin
 **/
@Data
@EqualsAndHashCode(callSuper = false)
public class SysRoleQueryParam extends PageQueryParam {

    @ApiModelProperty("角色ID")
    private Integer roleId;

    @ApiModelProperty("角色名称")
    private String roleName;

    @ApiModelProperty("角色类型（1：超级管理员，2管理人员，3运维人员，4操作人员，5客服人员）")
    private Integer roleType;

    @ApiModelProperty("角色描述")
    private String roleDesc;

    @ApiModelProperty("启用状态")
    private Boolean isEnable;

    @ApiModelProperty(value = "是否删除", hidden = true)
    private Boolean isDel;


}




