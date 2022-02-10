package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 *SysRegion 查询参数
 * @author 苏礼刚
 **/
@Data
@EqualsAndHashCode(callSuper = false)
public class SysRegionQueryParam extends PageQueryParam {
	@ApiModelProperty("主键")
	private Integer id;

	@ApiModelProperty("区域名称")
	private String regionName;

	@ApiModelProperty("父节点ID")
	private Integer regionParent;

	@ApiModelProperty("纬度")
	private String latitude;

	@ApiModelProperty("精度")
	private String longitude;

}




