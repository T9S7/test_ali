package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 *CheckInfo 查询参数
 * @author 苏礼刚
 **/
@Data
@EqualsAndHashCode(callSuper = false)
public class CheckInfoQueryParam extends PageQueryParam {
	@ApiModelProperty("id")
	private Integer id;

	@ApiModelProperty("contractAddr")
	private String contractAddr;

	@ApiModelProperty("物品id")
	private Long thingId;

	@ApiModelProperty("校验状态(0-失败，1-成功)")
	private Integer checkType;

	@ApiModelProperty("失败原因")
	private String fileInfo;

	@ApiModelProperty("用户id")
	private Integer userId;

	@ApiModelProperty("校验时间")
	private Long createTime;

	@ApiModelProperty("更新时间")
	private Long updateTime;

	@ApiModelProperty("是否删除")
	private Boolean isDel;

}




