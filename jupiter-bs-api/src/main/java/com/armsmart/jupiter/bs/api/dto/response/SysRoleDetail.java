package com.armsmart.jupiter.bs.api.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * SysRole详情DTO
 *
 * @author wei.lin
 **/
@Data
@ApiModel(description = "_response")
public class SysRoleDetail {

    @ApiModelProperty("角色ID")
    private Integer roleId;

    @ApiModelProperty("角色名称")
    private String roleName;

    @ApiModelProperty("角色类型（1：超级管理员，2管理人员，3运维人员，4操作人员，5客服人员）")
    private Integer roleType;

    @ApiModelProperty("角色描述")
    private String roleDesc;

    @ApiModelProperty("角色级别（值越小级别越高，低级别不能操作高级别）")
    private Integer roleLevel;

    @ApiModelProperty("启用状态")
    private Boolean isEnable;

    @ApiModelProperty("创建时间")
    private Long createTime;

    @ApiModelProperty("修改时间")
    private Long updateTime;



}



