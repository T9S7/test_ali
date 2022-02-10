package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * SmsInfo添加DTO
 * @author wei.lin
 **/
@Data
@ApiModel(description = "_request")
public class SmsInfoAddParam {



	@NotBlank(message="手机号不能为空")
	@Length(max= 16,message="手机号长度不能超过16")
	@ApiModelProperty(value = "手机号",required = true)
	private String mobile;

	@NotNull(message = "短信类型（1：注册）不能为空")
	@ApiModelProperty(value = "短信类型（1：注册）",required = true)
	private Integer smsType;

	@NotBlank(message="验证码不能为空")
	@Length(max= 6,message="验证码长度不能超过6")
	@ApiModelProperty(value = "验证码",required = true)
	private String verifyCode;

	@NotNull(message = "创建时间不能为空")
	@ApiModelProperty(value = "创建时间",required = true)
	private Long createTime;

	@NotNull(message = "过期时间不能为空")
	@ApiModelProperty(value = "过期时间",required = true)
	private Long expireTime;

	@NotNull(message = "是否删除不能为空")
	@ApiModelProperty(value = "是否删除",required = true)
	private Boolean isDel;


}



