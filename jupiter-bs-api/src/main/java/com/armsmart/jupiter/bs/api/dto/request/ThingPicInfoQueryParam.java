package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 *ThingPicInfo 查询参数
 * @author 苏礼刚
 **/
@Data
@EqualsAndHashCode(callSuper = false)
public class ThingPicInfoQueryParam extends PageQueryParam {
	@ApiModelProperty("主键ID")
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




