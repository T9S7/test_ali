package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 UserAuthentication修改DTO
 * @author wei.lin
 **/
@Data
@ApiModel(description = "_request")
public class UserAuthenticationUpdateParam {


	@NotNull(message = "序列号不能为空")
	@ApiModelProperty(value = "序列号", required = true)
	private Long id;

	@Length(max= 50,message="真实姓名长度不能超过50")
	@ApiModelProperty("真实姓名")
	private String name;

	@NotNull(message = "APP用户编号不能为空")
	@ApiModelProperty(value = "APP用户编号", required = true)
	private Integer userId;

	@NotBlank(message="证件号码不能为空")
	@Length(max= 18,message="证件号码长度不能超过18")
	@ApiModelProperty(value = "证件号码", required = true)
	private String certificateNo;

	@NotBlank(message="手机号不能为空")
	@Length(max= 11,message="手机号长度不能超过11")
	@ApiModelProperty(value = "手机号", required = true)
	private String mobile;

	@Length(max= 200,message="身份证正面照片长度不能超过200")
	@ApiModelProperty("身份证正面照片")
	private String certificateFace;

	@Length(max= 200,message="身份证反面照长度不能超过200")
	@ApiModelProperty("身份证反面照")
	private String certificateBack;


	@Length(max= 256,message="公钥模数长度不能超过256")
	@ApiModelProperty("公钥模数")
	private String publicKeyM;

	@Length(max= 256,message="公钥指数长度不能超过256")
	@ApiModelProperty("公钥指数")
	private String publicKeyE;

	@ApiModelProperty("创建时间")
	private Long createTime;

	@ApiModelProperty("是否删除")
	private Boolean isDel;


}



