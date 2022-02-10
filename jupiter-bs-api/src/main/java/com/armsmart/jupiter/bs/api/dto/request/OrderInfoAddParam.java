package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;


import javax.validation.constraints.NotNull;

/**
 * OrderInfo添加DTO
 *
 * @author wei.lin
 **/
@Data
@ApiModel(description = "_request")
public class OrderInfoAddParam {

    @NotNull(message = "产品ID不能为空")
    @ApiModelProperty(value = "产品ID", required = true)
    private Long thingId;

//    @NotNull(message = "产品名称不能为空")
//    @ApiModelProperty(value = "产品名称", required = true)
//    private String thingName;

    @NotNull(message = "拍卖ID不能为空")
    @ApiModelProperty(value = "拍卖ID", required = true)
    private Long thingSellId;

    @NotNull(message = "物品数量不能为空")
    @ApiModelProperty(value = "物品数量默认为1", required = true)
    private Integer amount;

    @ApiModelProperty("保险（单位：分）")
    private Long insurance;

//    @NotNull(message = "支付方式不能为空")
//    @ApiModelProperty(value = "支付方式（1：微信；2：支付宝）", required = true)
//    private Integer payType;

    @Length(max = 200, message = "买家收货地址长度不能超过200")
    @ApiModelProperty("买家收货地址")
    private String buyerAddress;

    @Length(max = 32, message = "买家姓名长度不能超过32")
    @ApiModelProperty("买家姓名")
    private String buyerName;

    @Length(max = 16, message = "买家联系电话长度不能超过16")
    @ApiModelProperty("买家联系电话")
    private String buyerMobile;

    @NotNull(message = "卖家用户ID不能为空")
    @ApiModelProperty(value = "卖家用户ID", required = true)
    private Integer sellerId;

    @NotNull(message = "买家用户ID不能为空")
    @ApiModelProperty(value = "买家用户ID", required = true)
    private Integer buyerId;


}



