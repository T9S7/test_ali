package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * UserAuth管理员添加DTO
 *
 * @author wei.lin
 **/
@Data
@ApiModel(description = "_request")
public class UserAuthAdminAddParam {


    @NotBlank(message = "手机号不能为空")
    @Length(max = 11, message = "手机号长度不能超过11")
    @ApiModelProperty(value = "手机号", required = true)
    private String mobile;

    @NotNull(message = "用户类型不能为空")
    @Range(min = 0, max = 1, message = "不支持的用户类型")
    @ApiModelProperty(value = "用户类型：0普通；1：鉴定师", required = true)
    private Integer userType;
}



