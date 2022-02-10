package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * OrderInfo修改DTO
 *
 * @author wei.lin
 **/
@Data
@ApiModel(description = "_request")
public class OrderInfoUpdateParam {


    @NotNull(message = "订单ID不能为空")
    @ApiModelProperty(value = "订单ID", required = true)
    private Long orderId;

    @ApiModelProperty("产品ID")
    private Long thingId;

    @ApiModelProperty("拍卖ID")
    private Long thingSellId;

    @ApiModelProperty("价格（单位：分）")
    private Long price;

    @ApiModelProperty("保险（单位：分）")
    private Long insurance;

    @Length(max = 200, message = "买家收货地址长度不能超过200")
    @ApiModelProperty("买家收货地址")
    private String buyerAddress;

    @Length(max = 32, message = "买家姓名长度不能超过32")
    @ApiModelProperty("买家姓名")
    private String buyerName;

    @Length(max = 16, message = "买家联系电话长度不能超过16")
    @ApiModelProperty("买家联系电话")
    private String buyerMobile;

    @ApiModelProperty("订单状态（1：待支付；2：待发货；3：已发货；4：已收货；8：交易成功；9：交易失败）")
    private Integer orderStatus;

    @ApiModelProperty("卖家用户ID")
    private Integer sellerId;

    @ApiModelProperty("买家用户ID")
    private Integer buyerId;

    @Length(max = 64, message = "快递单号长度不能超过64")
    @ApiModelProperty("快递单号")
    private String expressNumber;

    @Length(max = 20, message = "快递公司长度不能超过20")
    @ApiModelProperty("快递公司")
    private String expressCompany;
    
    @ApiModelProperty("可支付截止时间")
    private Long payDeadline;

    @ApiModelProperty("发货截止时间")
    private Long sendOutDeadline;


}



