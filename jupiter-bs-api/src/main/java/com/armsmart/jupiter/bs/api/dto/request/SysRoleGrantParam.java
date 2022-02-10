package com.armsmart.jupiter.bs.api.dto.request;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 角色授权dto
 *
 * @author wei.lin
 **/
@Data
@ApiModel(description = "_request")
public class SysRoleGrantParam {
    
    @ApiModelProperty(value = "角色ID", required = true)
    private Integer roleId;

    @ApiModelProperty(value = "菜单ID集合", required = true)
    private List<Integer> resourceIds;
}
