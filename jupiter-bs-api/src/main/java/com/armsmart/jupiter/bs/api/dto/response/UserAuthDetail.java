package com.armsmart.jupiter.bs.api.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *UserAuth详情DTO
 * @author wei.lin
 **/
@Data
@ApiModel(description = "_response")
public class UserAuthDetail {
    @ApiModelProperty("主键ID")
    private java.lang.Integer id;

    @ApiModelProperty("用户ID")
    private java.lang.Integer userId;

    @ApiModelProperty("登录类型")
    private java.lang.Integer identityType;

    @ApiModelProperty("标识")
    private java.lang.String identifier;

    @ApiModelProperty("密码凭证")
    private java.lang.String credential;

    @ApiModelProperty("是否删除")
    private java.lang.Boolean isDel;

}



