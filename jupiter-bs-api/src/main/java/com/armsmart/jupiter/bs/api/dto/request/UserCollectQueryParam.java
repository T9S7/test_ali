package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 *UserCollect 查询参数
 * @author 苏礼刚
 **/
@Data
@EqualsAndHashCode(callSuper = false)
public class UserCollectQueryParam extends PageQueryParam {
	@ApiModelProperty("序号")
	private Long id;

	@ApiModelProperty("收藏物品id")
	private Long thingId;

	@ApiModelProperty("收藏用户id")
	private Long userId;

	@ApiModelProperty("创建时间")
	private Long createTime;

	@ApiModelProperty("更新时间")
	private Long updateTime;

	@ApiModelProperty("是否删除")
	private Boolean isDel;

}




