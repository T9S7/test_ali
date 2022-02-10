package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 *AppNotice 查询参数
 * @author wei.lin
 **/
@Data
@EqualsAndHashCode(callSuper = false)
public class AppNoticeQueryParam extends PageQueryParam {
	@ApiModelProperty("主键ID")
	private Integer id;

	@ApiModelProperty("通知标题")
	private String title;

	@ApiModelProperty("内容")
	private String content;

	@ApiModelProperty("签名落款")
	private String sign;

	@ApiModelProperty("类型（0：维护通知）")
	private Integer category;

	@ApiModelProperty("是否启用")
	private Boolean enabled;

	@ApiModelProperty("创建时间")
	private Long createTime;

	@ApiModelProperty("更新时间")
	private Long updateTime;

	@ApiModelProperty("是否删除")
	private Boolean isDel;

}




