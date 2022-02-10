package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;


import javax.validation.constraints.NotNull;

/**
 * SysResource修改DTO
 *
 * @author wei.lin
 **/
@Data
@ApiModel(description = "_request")
public class SysResourceUpdateParam {


    @NotNull(message = "资源ID不能为空")
    @ApiModelProperty(value = "资源ID", required = true)
    private Integer resourceId;

    @NotBlank(message = "资源名称不能为空")
    @Length(max = 32, message = "资源名称长度不能超过32")
    @ApiModelProperty(value = "资源名称", required = true)
    private String resourceName;

    @NotNull(message = "资源类型（1：菜单；2：按钮；3：查看）不能为空")
    @ApiModelProperty(value = "资源类型（1：菜单；2：按钮；3：查看）", required = true)
    private Integer resourceType;

    @Length(max = 64, message = "访问路径长度不能超过64")
    @ApiModelProperty("访问路径")
    private String resourcePath;

    @Length(max = 32, message = "资源唯一标识长度不能超过32")
    @NotBlank(message = "资源唯一标识不能为空")
    @ApiModelProperty(value = "资源唯一标识", required = true)
    private String resourceKey;

    @ApiModelProperty("资源排序值")
    private Integer resourceSort;

    @ApiModelProperty("父ID")
    private Integer parentId;

    @ApiModelProperty("启用状态")
    private Boolean isEnable;


}



