package com.armsmart.jupiter.bs.api.entity;
import lombok.Data;


/**
 *UserAddress entity
 * @author wei.lin
 **/
@Data
public class UserAddress{
    /**
    *主键ID
    */
    private Integer id;

    /**
    *姓名
    */
    private String name;

    /**
    *手机号
    */
    private String mobile;


    /**
    *省份
    */
    private String province;

    /**
    *省份代码
    */
    private String provinceCode;

    /**
    *市
    */
    private String city;

    /**
    *市代码
    */
    private String cityCode;

    /**
    *区（县）
    */
    private String district;

    /**
    *区（县）代码
    */
    private String districtCode;

    /**
    *详细地址
    */
    private String address;

    /**
    *地址类型（1.收件地址，2.寄件地址）
    */
    private Integer addressType;

    /**
     *地址标签
     */
    private String addressLabel;

    /**
    *邮编
    */
    private String postcode;

    /**
    *用户ID
    */
    private Integer userId;

    /**
    *是否为默认
    */
    private Boolean isDefault;

    /**
    *创建时间
    */
    private Long createTime;

    /**
    *修改时间
    */
    private Long updateTime;

    /**
    *是否删除
    */
    private Boolean isDel;


}




