package com.armsmart.jupiter.bs.api.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;


import javax.validation.constraints.NotNull;

/**
 * OrderInfo详情DTO
 *
 * @author wei.lin
 **/
@Data
@ApiModel(description = "_response")
public class OrderInfoDetail {
    @ApiModelProperty("订单ID")
    private Long orderId;

    @ApiModelProperty("订单编号")
    private String orderSn;

    @ApiModelProperty("订单类别(1：普通物品；2：上链工具)")
    private Integer orderCategory;

    @ApiModelProperty("产品ID")
    private Long thingId;

    @ApiModelProperty("拍卖ID")
    private Long thingSellId;

    @ApiModelProperty("价格（单位：分）")
    private Long price;

    @ApiModelProperty("物品数量")
    private Integer amount;

    @ApiModelProperty("保险（单位：分）")
    private Long insurance;

    @ApiModelProperty("买家收货地址")
    private String buyerAddress;

    @ApiModelProperty("买家姓名")
    private String buyerName;

    @ApiModelProperty("买家联系电话")
    private String buyerMobile;

    @ApiModelProperty("订单状态（1：待支付；2：待发货；3：已发货；4：已收货；8：交易成功；9：交易失败）")
    private Integer orderStatus;

    @ApiModelProperty("卖家用户ID")
    private Integer sellerId;

    @ApiModelProperty("买家用户ID")
    private Integer buyerId;

    @ApiModelProperty("快递单号")
    private String expressNumber;

    @ApiModelProperty("快递公司")
    private String expressCompany;

    @ApiModelProperty("支付方式（1：微信；2：支付宝）")
    private Integer payType;

    @ApiModelProperty("支付时间")
    private Long payTime;

    @ApiModelProperty("可支付截止时间")
    private Long payDeadline;

    @ApiModelProperty("发货截止时间")
    private Long sendOutDeadline;

    @ApiModelProperty("创建时间")
    private Long createTime;

    @ApiModelProperty("修改时间")
    private Long updateTime;

    @ApiModelProperty("物品信息")
    private ThingInfoDetail thingInfo;

    @ApiModelProperty("卖家头像")
    private String sellerAvatar;

    @ApiModelProperty("卖家昵称")
    private String sellerNickname;

    @ApiModelProperty("交易失败原因-买家")
    private String buyerFailureReason = "";

    @ApiModelProperty("交易失败原因-卖家")
    private String sellerFailureReason = "";
}



