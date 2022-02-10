package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 *CompanyInfo 查询参数
 * @author 苏礼刚
 **/
@Data
@EqualsAndHashCode(callSuper = false)
public class CompanyInfoQueryParam extends PageQueryParam {
	@ApiModelProperty("id")
	private Integer id;

	@ApiModelProperty("企业名称")
	private String companyName;

	@ApiModelProperty("企业描述")
	private String companyDesc;

	@ApiModelProperty("企业类型")
	private Integer companyType;

	@ApiModelProperty("企业头像地址")
	private String companyUrl;

	@ApiModelProperty("是否官方认证")
	private Boolean isOfficial;

	@ApiModelProperty("createTime")
	private Long createTime;

	@ApiModelProperty("updateTime")
	private Long updateTime;

	@ApiModelProperty("isDel")
	private Boolean isDel;

}




