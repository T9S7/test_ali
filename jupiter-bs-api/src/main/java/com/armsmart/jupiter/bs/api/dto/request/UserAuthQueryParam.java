package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 *UserAuth 查询参数
 * @author wei.lin
 **/
@Data
@EqualsAndHashCode(callSuper = false)
public class UserAuthQueryParam extends PageQueryParam {
	@ApiModelProperty("主键ID")
	private Integer id;

	@ApiModelProperty("用户ID")
	private Integer userId;

	@ApiModelProperty("登录类型")
	private Integer identityType;

	@ApiModelProperty("标识")
	private String identifier;

	@ApiModelProperty("密码凭证")
	private String credential;

	@ApiModelProperty("是否删除")
	private Boolean isDel;

}




