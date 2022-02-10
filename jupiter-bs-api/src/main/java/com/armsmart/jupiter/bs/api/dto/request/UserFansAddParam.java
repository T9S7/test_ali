package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;


import javax.validation.constraints.NotNull;

/**
 * UserFans添加DTO
 * @author 苏礼刚
 **/
@Data
@ApiModel(description = "_request")
public class UserFansAddParam {



	@NotNull(message = "用户id不能为空")
	@ApiModelProperty(value = "用户id",required = true)
	private Long userId;

	@NotNull(message = "粉丝id不能为空")
	@ApiModelProperty(value = "粉丝id",required = true)
	private Long focusUserId;
//
//	@ApiModelProperty("1 粉丝 2互相关注")
//    private Integer focusState;


}



