package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;


import javax.validation.constraints.NotNull;

/**
 * AccountInfo添加DTO
 * @author 苏礼刚
 **/
@Data
@ApiModel(description = "_request")
public class AccountInfoAddParam {



	@NotNull(message = "用户id不能为空")
	@ApiModelProperty(value = "用户id",required = true)
	private Integer userId;

	@NotNull(message = "账户余额不能为空")
	@ApiModelProperty(value = "账户余额",required = true)
	private Long balance;

	@ApiModelProperty("冻结资金")
    private Long frozenCapital;

	@ApiModelProperty("保证金金额")
    private Long cashDeposit;

	@NotNull(message = "创建时间不能为空")
	@ApiModelProperty(value = "创建时间",required = true)
	private Long createTime;

	@ApiModelProperty("更新时间")
    private Long updateTime;

	@NotNull(message = "是否删除不能为空")
	@ApiModelProperty(value = "是否删除",required = true)
	private Boolean isDel;


}



