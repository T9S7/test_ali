package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 *RefundInfo 查询参数
 * @author 苏礼刚
 **/
@Data
@EqualsAndHashCode(callSuper = false)
public class RefundInfoQueryParam extends PageQueryParam {
	@ApiModelProperty("id")
	private Integer id;

	@ApiModelProperty("原始订单号")
	private Long orderId;

	@ApiModelProperty("会员号")
	private Integer userId;

	@ApiModelProperty("订单金额")
	private Long amount;

	@ApiModelProperty("退款订单状态（0-待退款；1-退款成功；2-退款失败）")
	private Integer orderStatus;

	@ApiModelProperty("createTime")
	private Long createTime;

	@ApiModelProperty("更新时间")
	private Long updateTime;

	@ApiModelProperty("是否删除")
	private Boolean isDel;

}




