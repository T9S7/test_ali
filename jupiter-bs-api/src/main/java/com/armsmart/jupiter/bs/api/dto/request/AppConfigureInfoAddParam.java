package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;


import javax.validation.constraints.NotNull;

/**
 * AppConfigureInfo添加DTO
 * @author 苏礼刚
 **/
@Data
@ApiModel(description = "_request")
public class AppConfigureInfoAddParam {

	@Length(max= 20,message="app版本长度不能超过20")
	@ApiModelProperty("app版本")
    private String appVersion;

	@Length(max= 20,message="联系电话长度不能超过20")
	@ApiModelProperty("联系电话")
    private String telephone;

	@Length(max= 200,message="公众号长度不能超过200")
	@ApiModelProperty("公众号")
    private String offAccount;

	@Length(max= 200,message="用户协议长度不能超过200")
	@ApiModelProperty("用户协议")
    private String userAgreement;

	@Length(max= 200,message="隐私政策长度不能超过200")
	@ApiModelProperty("隐私政策")
    private String privacyPolicy;

}



