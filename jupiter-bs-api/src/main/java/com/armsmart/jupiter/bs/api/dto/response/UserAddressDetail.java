package com.armsmart.jupiter.bs.api.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;


import javax.validation.constraints.NotNull;

/**
 *UserAddress详情DTO
 * @author wei.lin
 **/
@Data
@ApiModel(description = "_response")
public class UserAddressDetail {
    @ApiModelProperty("主键ID")
    private Integer id;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("手机号")
    private String mobile;


    @ApiModelProperty("省份")
    private String province;

    @ApiModelProperty("省份代码")
    private String provinceCode;

    @ApiModelProperty("市")
    private String city;

    @ApiModelProperty("市代码")
    private String cityCode;

    @ApiModelProperty("区（县）")
    private String district;

    @ApiModelProperty("区（县）代码")
    private String districtCode;

    @ApiModelProperty("详细地址")
    private String address;

    @ApiModelProperty("地址类型（1.收件地址，2.寄件地址）")
    private Integer addressType;

    @ApiModelProperty("地址标签")
    private String addressLabel;

    @ApiModelProperty("邮编")
    private String postcode;

    @ApiModelProperty("用户ID")
    private Integer userId;

    @ApiModelProperty("是否为默认")
    private Boolean isDefault;

    @ApiModelProperty("创建时间")
    private Long createTime;

    @ApiModelProperty("修改时间")
    private Long updateTime;

    @ApiModelProperty("是否删除")
    private Boolean isDel;

}



