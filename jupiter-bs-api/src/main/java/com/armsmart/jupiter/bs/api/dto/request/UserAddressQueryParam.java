package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * UserAddress 查询参数
 *
 * @author wei.lin
 **/
@Data
@EqualsAndHashCode(callSuper = false)
public class UserAddressQueryParam extends PageQueryParam {

    @ApiModelProperty("主键ID")
    private Integer id;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("手机号")
    private String mobile;

    @ApiModelProperty("地址标签")
    private String addressLabel;

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

    @ApiModelProperty(value = "邮编", hidden = true)
    private java.lang.String postcode;

    @ApiModelProperty("用户ID")
    private Integer userId;

    @ApiModelProperty(value = "是否删除")
    private java.lang.Boolean isDel;

    @ApiModelProperty(value = "是否默认")
    private java.lang.Boolean isDefault;


}




