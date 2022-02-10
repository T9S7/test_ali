package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;


import javax.validation.constraints.NotNull;

/**
 * UserBankInfo添加DTO
 * @author 苏礼刚
 **/
@Data
@ApiModel(description = "_request")
public class UserBankInfoAddParam {



	@NotBlank(message="爱较真用户userId不能为空")
	@Length(max= 20,message="爱较真用户userId长度不能超过20")
	@ApiModelProperty(value = "爱较真用户userId",required = true)
	private String userId;

	@Length(max= 50,message="银行别名长度不能超过50")
	@ApiModelProperty("银行别名")
    private String bankName;

	@Length(max= 20,message="银行代码长度不能超过20")
	@ApiModelProperty("银行代码")
    private String bankCode;

	@NotBlank(message="银行行号不能为空")
	@Length(max= 30,message="银行行号长度不能超过30")
	@ApiModelProperty(value = "银行行号",required = true)
	private String cardNo;

	@NotBlank(message="绑定手机号不能为空")
	@Length(max= 11,message="绑定手机号长度不能超过11")
	@ApiModelProperty(value = "绑定手机号",required = true)
	private String phone;

	@NotBlank(message="银行卡对应身份证用户姓名不能为空")
	@Length(max= 50,message="银行卡对应身份证用户姓名长度不能超过50")
	@ApiModelProperty(value = "银行卡对应身份证用户姓名",required = true)
	private String name;

	@NotNull(message = "绑卡方式 默认是7不能为空")
	@ApiModelProperty(value = "绑卡方式 默认是7",required = true)
	private Long cardCheck;

	@NotBlank(message="身份证号码不能为空")
	@Length(max= 20,message="身份证号码长度不能超过20")
	@ApiModelProperty(value = "身份证号码",required = true)
	private String identityNo;

	@Length(max= 10,message="有效期长度不能超过10")
	@ApiModelProperty("有效期")
    private String validate;

	@Length(max= 10,message="cvv2长度不能超过10")
	@ApiModelProperty("cvv2")
    private String cvv2;

	@Length(max= 30,message="支付行号长度不能超过30")
	@ApiModelProperty("支付行号")
    private String unionBank;

	@Length(max= 30,message="流水号长度不能超过30")
	@ApiModelProperty("流水号")
    private String tranceNum;

	@Length(max= 20,message="申请时间长度不能超过20")
	@ApiModelProperty("申请时间")
    private String transDate;

	@Length(max= 2,message="银行卡类型(1储蓄卡 2信用卡)长度不能超过2")
	@ApiModelProperty("银行卡类型(1储蓄卡 2信用卡)")
    private String cardType;

	@Length(max= 20,message="签约协议号长度不能超过20")
	@ApiModelProperty("签约协议号")
    private String agreementNo;

	@NotNull(message = "创建时间不能为空")
	@ApiModelProperty(value = "创建时间",required = true)
	private Long createTime;

	@NotNull(message = "是否删除不能为空")
	@ApiModelProperty(value = "是否删除",required = true)
	private Boolean isDel;


}



