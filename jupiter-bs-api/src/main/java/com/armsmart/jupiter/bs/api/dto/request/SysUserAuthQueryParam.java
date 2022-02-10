package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 *SysUserAuth 查询参数
 * @author 苏礼刚
 **/
@Data
@EqualsAndHashCode(callSuper = false)
public class SysUserAuthQueryParam extends PageQueryParam {
	@ApiModelProperty("主键ID")
	private Integer id;

	@ApiModelProperty("用户ID")
	private Integer userId;

	@ApiModelProperty("登录类型（1：手机、2：账号、3：邮箱）")
	private Integer identityType;

	@ApiModelProperty("身份标识")
	private String identifier;

	@ApiModelProperty("密码凭证")
	private String credential;

	@ApiModelProperty("是否删除")
	private Boolean isDel;

}




