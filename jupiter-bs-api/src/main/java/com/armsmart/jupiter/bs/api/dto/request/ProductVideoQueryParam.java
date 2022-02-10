package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 *ProductVideo 查询参数
 * @author 林伟
 **/
@Data
@EqualsAndHashCode(callSuper = false)
public class ProductVideoQueryParam extends PageQueryParam {
	@ApiModelProperty("主键")
	private Long id;

	@ApiModelProperty("产品ID")
	private Long productId;

	@ApiModelProperty("视频地址")
	private String videoUrl;

	@ApiModelProperty("视频下标")
	private Integer videoIndex;

	@ApiModelProperty("创建时间")
	private Long createTime;

	@ApiModelProperty("修改时间")
	private Long updateTime;

	@ApiModelProperty("是否删除")
	private Boolean isDel;

}




