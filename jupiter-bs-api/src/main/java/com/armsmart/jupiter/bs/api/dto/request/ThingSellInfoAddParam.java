package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;


import javax.validation.constraints.NotNull;

/**
 * ThingSellInfo添加DTO
 *
 * @author 苏礼刚
 **/
@Data
@ApiModel(description = "_request")
public class ThingSellInfoAddParam {


    @NotNull(message = "物品ID不能为空")
    @ApiModelProperty(value = "物品ID", required = true)
    private Long thingId;

    @NotNull(message = "发布类型 1 一口价；2 拍卖不能为空")
    @ApiModelProperty(value = "发布类型 1 一口价；2 拍卖", required = true)
    private Integer sellType;

    @NotBlank(message = "卖家寄语不能为空")
    @Length(max = 2000, message = "卖家寄语长度不能超过2000")
    @ApiModelProperty(value = "卖家寄语", required = true)
    private String sellerInfo;

    @ApiModelProperty("一口价价格")
    private Long thingPrice;

    @ApiModelProperty("拍卖 卖家估价")
    @Range(min = 10000, message = "估价最低不能低于100元")
    private Long sellerPrice;

    @ApiModelProperty("拍卖 保证金")
    private Long margin;

    @ApiModelProperty("拍卖 保证金订单号")
    private String marginOrderId;

    @ApiModelProperty("拍卖 起拍价")
    private Long sellStartPrice;

    @ApiModelProperty("拍卖 加价幅度")
    private Long sellAddPrice;

    @ApiModelProperty("拍卖开始时间")
    private Long auctionStartTime;

    @ApiModelProperty("拍卖结束时间")
    private Long auctionEndTime;


}



