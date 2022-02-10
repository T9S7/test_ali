package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;


import javax.validation.constraints.NotNull;

/**
 AccountCashDetail修改DTO
 * @author 苏礼刚
 **/
@Data
@ApiModel(description = "_request")
public class AccountCashDetailUpdateParam {


	@NotNull(message = "主键不能为空")
	@ApiModelProperty(value = "主键", required = true)
	private Integer id;

	@ApiModelProperty("账户id")
	private Integer accountId;

	@ApiModelProperty("保证金状态(1.代收未代付；2.已代付；3.已退还；4.转账(平台账户转出))")
	private Integer tradeType;

	@ApiModelProperty("收款方")
	private String payee;

	@ApiModelProperty("交易时间")
	private java.util.Date tradeTime;

	@ApiModelProperty("交易订单号")
	private Long tradeOrderNo;

	@ApiModelProperty("交易前金额")
	private Long preAccountBalance;

	@ApiModelProperty("交易金额")
	private Long tradeMoney;

	@ApiModelProperty("交易后保证金余额")
	private Long cashAccountBalance;

	@ApiModelProperty("拍卖id")
	private Integer sellId;

	@ApiModelProperty("交易时间")
	private Long createTime;

	@ApiModelProperty("是否删除")
	private Boolean isDel;


}



