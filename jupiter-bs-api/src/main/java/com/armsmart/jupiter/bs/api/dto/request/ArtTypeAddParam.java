package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;


import javax.validation.constraints.NotNull;

/**
 * ArtType添加DTO
 * @author 苏礼刚
 **/
@Data
@ApiModel(description = "_request")
public class ArtTypeAddParam {


//
//	@NotNull(message = "级别不能为空")
//	@ApiModelProperty(value = "级别",required = true)
//	private Integer lever;

	@NotBlank(message="类别名称不能为空")
	@Length(max= 100,message="类别名称长度不能超过100")
	@ApiModelProperty(value = "类别名称",required = true)
	private String typeName;

	@NotNull(message = "父id不能为空")
	@ApiModelProperty(value = "父id",required = true)
	private Integer parentId;


}



