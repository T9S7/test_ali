package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 *ThingSellInfo 查询参数
 * @author 苏礼刚
 **/
@Data
@EqualsAndHashCode(callSuper = false)
public class ThingSellInfoQueryParam extends PageQueryParam {
	@ApiModelProperty("主键ID")
	private Long id;

	@ApiModelProperty("物品ID")
	private Long thingId;

	@ApiModelProperty("发布类型 1 一口价；2 拍卖")
	private Integer sellType;

	@ApiModelProperty("卖家寄语")
	private String sellerInfo;

	@ApiModelProperty("一口价价格")
	private Long thingPrice;

	@ApiModelProperty("拍卖 卖家估价")
	private Long sellerPrice;

	@ApiModelProperty("拍卖 保证金")
	private Long margin;

	@ApiModelProperty("拍卖 起拍价")
	private Long sellStartPrice;

	@ApiModelProperty("拍卖 加价幅度")
	private Long sellAddPrice;

	@ApiModelProperty("拍卖开始时间")
	private Long auctionStartTime;

	@ApiModelProperty("拍卖结束时间")
	private Long auctionEndTime;

	@ApiModelProperty("上架状态")
	private Boolean putOn;

	@ApiModelProperty("创建时间")
	private Long createTime;

	@ApiModelProperty("修改时间")
	private Long updateTime;

	@ApiModelProperty("是否删除")
	private Boolean isDel;

}




