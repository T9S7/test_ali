package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 *ProductPic 查询参数
 * @author 林伟
 **/
@Data
@EqualsAndHashCode(callSuper = false)
public class ProductPicQueryParam extends PageQueryParam {
	@ApiModelProperty("主键")
	private Long id;

	@ApiModelProperty("产品ID")
	private Long productId;

	@ApiModelProperty("图片地址")
	private String picUrl;

	@ApiModelProperty("图片下标")
	private Integer picIndex;

	@ApiModelProperty("图片MD5码")
	private String picMd5;

	@ApiModelProperty("创建时间")
	private Long createTime;

	@ApiModelProperty("修改时间")
	private Long updateTime;

	@ApiModelProperty("是否删除")
	private Boolean isDel;

}




