package com.armsmart.jupiter.bs.api.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


import java.util.List;

/**
 * SysResource详情DTO
 *
 * @author wei.lin
 **/
@Data
@ApiModel(description = "_response")
public class SysResourceDetail {

    @ApiModelProperty("资源ID")
    private Integer resourceId;

    @ApiModelProperty("资源名称")
    private String resourceName;

    @ApiModelProperty("资源类型（1：菜单；2：按钮；3：查看）")
    private Integer resourceType;

    @ApiModelProperty("访问路径")
    private String resourcePath;

    @ApiModelProperty("资源唯一标识")
    private String resourceKey;

    @ApiModelProperty("资源排序值")
    private Integer resourceSort;

    @ApiModelProperty("父ID")
    private Integer parentId;

    @ApiModelProperty("启用状态")
    private Boolean isEnable;

    @ApiModelProperty("资源子集")
    private List<SysResourceDetail> children;

}



