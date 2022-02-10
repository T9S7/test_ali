package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 *AppConfigureInfo 查询参数
 * @author 苏礼刚
 **/
@Data
@EqualsAndHashCode(callSuper = false)
public class AppConfigureInfoQueryParam extends PageQueryParam {
	@ApiModelProperty("序号")
	private Integer id;

	@ApiModelProperty("app版本")
	private String appVersion;

	@ApiModelProperty("联系电话")
	private String telephone;

	@ApiModelProperty("公众号")
	private String offAccount;

	@ApiModelProperty("用户协议")
	private String userAgreement;

	@ApiModelProperty("隐私政策")
	private String privacyPolicy;

	@ApiModelProperty("预留1")
	private String cloume1;

	@ApiModelProperty("创建时间")
	private Long createTime;

	@ApiModelProperty("更新时间")
	private Long updateTime;

	@ApiModelProperty("是否删除")
	private Boolean isDel;

}




