package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;


/**
 * UserAddress 查询参数
 *
 * @author wei.lin
 **/
@Data
@EqualsAndHashCode(callSuper = false)
public class UserAddressMyQueryParam {

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("手机号")
    private String mobile;

    @ApiModelProperty("地址标签")
    private String addressLabel;

    @ApiModelProperty("地址类型（1.收件地址，2.寄件地址）")
    private Integer addressType;

    @ApiModelProperty(value = "用户ID", required = true)
    @NotNull(message = "用户ID为必填参数")
    private Integer userId;


}




