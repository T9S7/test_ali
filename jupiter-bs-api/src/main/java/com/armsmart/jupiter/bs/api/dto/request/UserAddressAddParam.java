package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * UserAddress添加DTO
 *
 * @author wei.lin
 **/
@Data
@ApiModel(description = "_request")
public class UserAddressAddParam {


    @NotBlank(message = "姓名不能为空")
    @Length(max = 32, message = "姓名长度不能超过32")
    @ApiModelProperty(value = "姓名", required = true)
    private String name;

    @NotBlank(message = "手机号不能为空")
    @Length(max = 11, message = "手机号长度不能超过11")
    @ApiModelProperty(value = "手机号", required = true)
    private String mobile;


    @NotBlank(message = "省份不能为空")
    @Length(max = 16, message = "省份长度不能超过16")
    @ApiModelProperty(value = "省份", required = true)
    private String province;

    @NotBlank(message = "省份代码不能为空")
    @Length(max = 10, message = "省份代码长度不能超过10")
    @ApiModelProperty(value = "省份代码", required = true)
    private String provinceCode;

    @NotBlank(message = "市不能为空")
    @Length(max = 16, message = "市长度不能超过16")
    @ApiModelProperty(value = "市", required = true)
    private String city;

    @NotBlank(message = "市代码不能为空")
    @Length(max = 10, message = "市代码长度不能超过10")
    @ApiModelProperty(value = "市代码", required = true)
    private String cityCode;

    @NotBlank(message = "区（县）不能为空")
    @Length(max = 20, message = "区（县）长度不能超过20")
    @ApiModelProperty(value = "区（县）", required = true)
    private String district;

    @NotBlank(message = "区（县）代码不能为空")
    @Length(max = 10, message = "区（县）代码长度不能超过10")
    @ApiModelProperty(value = "区（县）代码", required = true)
    private String districtCode;

    @NotBlank(message = "详细地址不能为空")
    @Length(max = 200, message = "详细地址长度不能超过200")
    @ApiModelProperty(value = "详细地址", required = true)
    private String address;

//    @NotNull(message = "地址类型（1.收件地址，2.寄件地址）")
    @ApiModelProperty(value = "地址类型（1.收件地址，2.寄件地址）")
    private Integer addressType;

    @Length(max = 5, message = "地址标签长度不能超过5")
    @ApiModelProperty("地址标签")
    private String addressLabel;

    @Length(max = 10, message = "邮编长度不能超过10")
    @ApiModelProperty("邮编")
    private String postcode;

    @NotNull(message = "用户ID不能为空")
    @ApiModelProperty(value = "用户ID", required = true)
    private Integer userId;

    @ApiModelProperty(value = "是否为默认")
    private Boolean isDefault;


}



