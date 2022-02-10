package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 *ArtOrderInfo 查询参数
 * @author wei.lin
 **/
@Data
@EqualsAndHashCode(callSuper = false)
public class ArtOrderInfoQueryParam extends PageQueryParam {
	@ApiModelProperty("订单ID")
	private Long orderId;

	@ApiModelProperty("店铺艺术品ID")
	private Long storeArtId;

	@ApiModelProperty("艺术品ID")
	private Long artId;

	@ApiModelProperty("价格（单位：分）")
	private Long price;

	@ApiModelProperty("买家收货地址")
	private String buyerAddress;

	@ApiModelProperty("买家姓名")
	private String buyerName;

	@ApiModelProperty("买家联系电话")
	private String buyerMobile;

	@ApiModelProperty("订单状态（1：待填写收货地址；2：待发货；3：已发货；4：交易成功）")
	private Integer orderStatus;

	@ApiModelProperty("卖家用户ID")
	private Integer sellerId;

	@ApiModelProperty("买家用户ID")
	private Integer buyerId;

	@ApiModelProperty("店铺ID")
	private Long storeId;

	@ApiModelProperty("快递单号")
	private String expressNumber;

	@ApiModelProperty("快递公司")
	private String expressCompany;

	@ApiModelProperty("创建时间")
	private Long createTime;

	@ApiModelProperty("修改时间")
	private Long updateTime;

	@ApiModelProperty("是否删除")
	private Boolean isDel;

}




