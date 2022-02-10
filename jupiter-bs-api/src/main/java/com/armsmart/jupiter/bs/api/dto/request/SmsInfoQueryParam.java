package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 *SmsInfo 查询参数
 * @author wei.lin
 **/
@Data
@EqualsAndHashCode(callSuper = false)
public class SmsInfoQueryParam extends PageQueryParam {
	@ApiModelProperty("主键ID")
	private Integer id;

	@ApiModelProperty("手机号")
	private String mobile;

	@ApiModelProperty("短信类型（1：注册）")
	private Integer smsType;

	@ApiModelProperty("验证码")
	private String verifyCode;

	@ApiModelProperty("创建时间")
	private Long createTime;

	@ApiModelProperty("过期时间")
	private Long expireTime;

	@ApiModelProperty("是否删除")
	private Boolean isDel;

}




