package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * SysRole修改DTO
 *
 * @author wei.lin
 **/
@Data
@ApiModel(description = "_request")
public class SysRoleUpdateParam {

    @NotNull(message = "角色ID不能为空")
    @ApiModelProperty(value = "角色ID", required = true)
    private Integer roleId;

    @NotBlank(message = "角色名称不能为空")
    @Length(max = 32, message = "角色名称长度不能超过32")
    @ApiModelProperty(value = "角色名称", required = true)
    private String roleName;

    @ApiModelProperty(value = "角色类型（1：超级管理员，2管理人员，3运维人员，4操作人员，5客服人员）", required = true)
    @Min(value = 2, message = "不支持的角色类型")
    @Max(value = 5, message = "不支持的角色类型")
    private Integer roleType;

    @Length(max = 64, message = "角色描述长度不能超过64")
    @ApiModelProperty("角色描述")
    private String roleDesc;

    @NotNull(message = "资源ID集合不能为空")
    @ApiModelProperty(value = "资源ID集合", required = true)
    private List<Integer> resourceIds;

}



