package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 UserAuth修改DTO
 * @author wei.lin
 **/
@Data
@ApiModel(description = "_request")
public class UserAuthUpdateParam {


	@NotNull(message = "主键ID不能为空")
	@ApiModelProperty(value = "主键ID", required = true)
	private Integer id;

	@NotNull(message = "用户ID不能为空")
	@ApiModelProperty(value = "用户ID", required = true)
	private Integer userId;

	@NotNull(message = "登录类型不能为空")
	@ApiModelProperty(value = "登录类型", required = true)
	private Integer identityType;

	@NotBlank(message="标识不能为空")
	@Length(max= 32,message="标识长度不能超过32")
	@ApiModelProperty(value = "标识", required = true)
	private String identifier;

	@NotBlank(message="密码凭证不能为空")
	@Length(max= 64,message="密码凭证长度不能超过64")
	@ApiModelProperty(value = "密码凭证", required = true)
	private String credential;

	@NotNull(message = "是否删除不能为空")
	@ApiModelProperty(value = "是否删除", required = true)
	private Boolean isDel;


}



