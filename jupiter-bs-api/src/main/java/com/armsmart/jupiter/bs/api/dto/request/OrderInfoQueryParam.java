package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;


/**
 * OrderInfo 查询参数
 *
 * @author wei.lin
 **/
@Data
@EqualsAndHashCode(callSuper = false)
public class OrderInfoQueryParam extends PageQueryParam {
    @ApiModelProperty("订单ID")
    private Long orderId;

    @ApiModelProperty("订单编号")
    private String orderSn;

    @ApiModelProperty("产品ID")
    private Long thingId;

    @ApiModelProperty("产品名称")
    private String thingName;

    @ApiModelProperty("拍卖ID")
    private Long thingSellId;

    @ApiModelProperty("价格（单位：分）")
    private Long price;

    @ApiModelProperty("保险（单位：分）")
    private Long insurance;

    @ApiModelProperty("买家收货地址")
    private String buyerAddress;

    @ApiModelProperty("买家姓名")
    private String buyerName;

    @ApiModelProperty("买家联系电话")
    private String buyerMobile;

    @ApiModelProperty("订单状态（1：待支付；2：待发货；3：已发货；4：已收货；8：交易成功；9：交易失败）")
    private List<Integer> orderStatus;

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

    @ApiModelProperty("支付状态 0.未支付 1.支付成功 2.支付失败")
    private Integer payStatus;

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

    @ApiModelProperty("是否删除")
    private Boolean isDel;

    @ApiModelProperty("买家是否删除")
    private Boolean buyerDel;

    @ApiModelProperty("卖家是否删除")
    private Boolean sellerDel;

//    private Integer orderCategory;
    private List<Integer> orderCategory;

}




