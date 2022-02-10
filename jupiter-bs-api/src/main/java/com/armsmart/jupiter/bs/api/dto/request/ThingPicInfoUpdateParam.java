package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;


import javax.validation.constraints.NotNull;

/**
 ThingPicInfo修改DTO
 * @author 苏礼刚
 **/
@Data
@ApiModel(description = "_request")
public class ThingPicInfoUpdateParam {


	@NotNull(message = "主键ID不能为空")
	@ApiModelProperty(value = "主键ID", required = true)
	private Long id;

	@ApiModelProperty("物品ID")
	private Long thingId;

	@ApiModelProperty("图片地址")
	private String picUrl;

	@ApiModelProperty("图片md5码")
	private String picMd5;

	@ApiModelProperty("创建时间")
	private Long createTime;

	@ApiModelProperty("修改时间")
	private Long updateTime;

	@ApiModelProperty("是否删除")
	private Boolean isDel;


}



