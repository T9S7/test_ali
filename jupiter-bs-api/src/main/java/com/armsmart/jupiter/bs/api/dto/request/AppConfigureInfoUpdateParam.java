package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;


import javax.validation.constraints.NotNull;

/**
 AppConfigureInfo修改DTO
 * @author 苏礼刚
 **/
@Data
@ApiModel(description = "_request")
public class AppConfigureInfoUpdateParam {


	@NotNull(message = "序号不能为空")
	@ApiModelProperty(value = "序号", required = true)
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



