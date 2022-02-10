package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;


import javax.validation.constraints.NotNull;

/**
 * CheckInfo添加DTO
 * @author 苏礼刚
 **/
@Data
@ApiModel(description = "_request")
public class CheckInfoAddParam {



	@NotBlank(message="contractAddr不能为空")
	@Length(max= 64,message="contractAddr长度不能超过64")
	@ApiModelProperty(value = "contractAddr",required = true)
	private String contractAddr;

	@ApiModelProperty("物品id")
    private Long thingId;

	@NotNull(message = "校验状态(0-失败，1-成功)不能为空")
	@ApiModelProperty(value = "校验状态(0-失败，1-成功)",required = true)
	private Integer checkType;

	@Length(max= 200,message="失败原因长度不能超过200")
	@ApiModelProperty("失败原因")
    private String fileInfo;

	@NotNull(message = "用户id不能为空")
	@ApiModelProperty(value = "用户id",required = true)
	private Integer userId;


}



