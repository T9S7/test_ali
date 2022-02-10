package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;


import javax.validation.constraints.NotNull;

/**
 ProductInfo修改DTO
 * @author 林伟
 **/
@Data
@ApiModel(description = "_request")
public class ProductInfoUpdateParam {


	@NotNull(message = "产品ID不能为空")
	@ApiModelProperty(value = "产品ID", required = true)
	private Long id;

	@ApiModelProperty("产品名称")
	private String name;

	@ApiModelProperty("一级类型")
	private Integer typeLevelOne;

	@ApiModelProperty("二级类型")
	private Integer typeLevelTwo;

	@ApiModelProperty("作者")
	private String author;

	@ApiModelProperty("拥有者")
	private String owner;

	@ApiModelProperty("序号")
	private String serialNum;

	@ApiModelProperty("介绍")
	private String description;

	@ApiModelProperty("规格")
	private String specs;

	@ApiModelProperty("库存")
	private Integer stock;

	@ApiModelProperty("上架状态")
	private Boolean putOn;

	@ApiModelProperty("审核状态（0：待审核、1：审核中、2：已驳回、3：重新审核）")
	private Integer auditStatus;

	@ApiModelProperty("审核意见")
	private String auditComments;

	@ApiModelProperty("上架时间")
	private Long putOnTime;

	@ApiModelProperty("创建时间")
	private Long createTime;

	@ApiModelProperty("修改时间")
	private Long updateTime;

	@ApiModelProperty("是否删除")
	private Boolean isDel;


}



