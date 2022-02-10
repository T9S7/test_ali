package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 *AccountInfo 查询参数
 * @author 苏礼刚
 **/
@Data
@EqualsAndHashCode(callSuper = false)
public class AccountInfoQueryParam extends PageQueryParam {
	@ApiModelProperty("账户id")
	private Integer accountId;

	@ApiModelProperty("用户id")
	private Integer userId;

	@ApiModelProperty("账户余额")
	private Long balance;

	@ApiModelProperty("冻结资金")
	private Long frozenCapital;

	@ApiModelProperty("保证金金额")
	private Long cashDeposit;

	@ApiModelProperty("创建时间")
	private Long createTime;

	@ApiModelProperty("更新时间")
	private Long updateTime;

	@ApiModelProperty("是否删除")
	private Boolean isDel;

}




