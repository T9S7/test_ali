package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 ArtPicInfo修改DTO
 * @author wei.lin
 **/
@Data
@ApiModel(description = "_request")
public class ArtPicInfoUpdateParam {


	@NotNull(message = "主键ID不能为空")
	@ApiModelProperty(value = "主键ID", required = true)
	private Long id;

	@ApiModelProperty("艺术品ID")
	private Long artId;

	@Length(max= 128,message="图片地址长度不能超过128")
	@ApiModelProperty("图片地址")
	private String picUrl;

	@Length(max= 36,message="图片md5码长度不能超过36")
	@ApiModelProperty("图片md5码")
	private String picMd5;

	@ApiModelProperty("创建时间")
	private Long createTime;

	@ApiModelProperty("修改时间")
	private Long updateTime;

	@ApiModelProperty("是否删除")
	private Boolean isDel;


}



