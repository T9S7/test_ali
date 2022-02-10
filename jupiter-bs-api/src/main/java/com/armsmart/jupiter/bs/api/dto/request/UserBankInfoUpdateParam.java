package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;


import javax.validation.constraints.NotNull;

/**
 UserBankInfo修改DTO
 * @author 苏礼刚
 **/
@Data
@ApiModel(description = "_request")
public class UserBankInfoUpdateParam {


	@NotNull(message = "序号不能为空")
	@ApiModelProperty(value = "序号", required = true)
	private Integer id;

	@ApiModelProperty("爱较真用户userId")
	private String userId;

	@ApiModelProperty("银行别名")
	private String bankName;

	@ApiModelProperty("银行代码")
	private String bankCode;

	@ApiModelProperty("银行行号")
	private String cardNo;

	@ApiModelProperty("绑定手机号")
	private String phone;

	@ApiModelProperty("银行卡对应身份证用户姓名")
	private String name;

	@ApiModelProperty("绑卡方式 默认是7")
	private Long cardCheck;

	@ApiModelProperty("身份证号码")
	private String identityNo;

	@ApiModelProperty("有效期")
	private String validate;

	@ApiModelProperty("cvv2")
	private String cvv2;

	@ApiModelProperty("支付行号")
	private String unionBank;

	@ApiModelProperty("流水号")
	private String tranceNum;

	@ApiModelProperty("申请时间")
	private String transDate;

	@ApiModelProperty("银行卡类型(1储蓄卡 2信用卡)")
	private String cardType;

	@ApiModelProperty("签约协议号")
	private String agreementNo;

	@ApiModelProperty("创建时间")
	private Long createTime;

	@ApiModelProperty("是否删除")
	private Boolean isDel;


}



