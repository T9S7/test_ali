package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;


import javax.validation.constraints.NotNull;

/**
 * UserCollect添加DTO
 * @author 苏礼刚
 **/
@Data
@ApiModel(description = "_request")
public class UserCollectAddParam {



	@NotNull(message = "收藏物品id不能为空")
	@ApiModelProperty(value = "收藏物品id",required = true)
	private Long thingId;

	@NotNull(message = "收藏用户id不能为空")
	@ApiModelProperty(value = "收藏用户id",required = true)
	private Long userId;

}



