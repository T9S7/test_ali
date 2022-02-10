package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 *BidInfo 查询参数
 * @author wei.lin
 **/
@Data
@EqualsAndHashCode(callSuper = false)
public class BidInfoQueryParam extends PageQueryParam {
	@ApiModelProperty("竞标ID")
	private Long bidId;

	@ApiModelProperty("竞标价格（分）")
	private Long bidPrice;

	@ApiModelProperty(value = "拍卖编号")
	private Long sellId;

	@ApiModelProperty("竞拍人ID")
	private Integer userId;

	@ApiModelProperty("竞拍人昵称")
	private String nickname;

	@ApiModelProperty("竞标时间")
	private Long bidTime;

	@ApiModelProperty("是否删除")
	private Boolean isDel;

	private Integer bidStates;

	private Long orderDeadline;
}




