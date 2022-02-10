package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;


import javax.validation.constraints.NotNull;

/**
 UserFans修改DTO
 * @author 苏礼刚
 **/
@Data
@ApiModel(description = "_request")
public class UserFansUpdateParam {


	@NotNull(message = "序号不能为空")
	@ApiModelProperty(value = "序号", required = true)
	private Long id;

	@ApiModelProperty("用户id")
	private Long userId;

	@ApiModelProperty("粉丝id")
	private Long focusUserId;

	@ApiModelProperty("1 粉丝 2互相关注")
	private Integer focusState;

	@ApiModelProperty("创建时间")
	private Long createTime;

	@ApiModelProperty("更新时间")
	private Long updateTime;

	@ApiModelProperty("是否删除")
	private Boolean isDel;


}



