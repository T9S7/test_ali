package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;


import javax.validation.constraints.NotNull;

/**
 RefundInfo修改DTO
 * @author 苏礼刚
 **/
@Data
@ApiModel(description = "_request")
public class RefundInfoUpdateParam {


	@NotNull(message = "id不能为空")
	@ApiModelProperty(value = "id", required = true)
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



