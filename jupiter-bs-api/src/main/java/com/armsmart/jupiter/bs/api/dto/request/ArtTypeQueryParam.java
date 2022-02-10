package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 *ArtType 查询参数
 * @author 苏礼刚
 **/
@Data
@EqualsAndHashCode(callSuper = false)
public class ArtTypeQueryParam extends PageQueryParam {
	@ApiModelProperty("序号")
	private Integer id;

	@ApiModelProperty("级别")
	private Integer lever;

	@ApiModelProperty("类别名称")
	private String typeName;

	@ApiModelProperty("父id")
	private Integer parentId;

	@ApiModelProperty("创建时间")
	private Long createTime;

	@ApiModelProperty("更新时间")
	private Long updateTime;

	@ApiModelProperty("是否删除")
	private Boolean isDel;

}




