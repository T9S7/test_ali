package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;


import javax.validation.constraints.NotNull;

/**
 * CompanyInfo添加DTO
 * @author 苏礼刚
 **/
@Data
@ApiModel(description = "_request")
public class CompanyInfoAddParam {



	@NotBlank(message="企业名称不能为空")
	@Length(max= 200,message="企业名称长度不能超过200")
	@ApiModelProperty(value = "企业名称",required = true)
	private String companyName;

	@Length(max= 2000,message="企业描述长度不能超过2000")
	@ApiModelProperty("企业描述")
    private String companyDesc;

	@ApiModelProperty("企业类型")
    private Integer companyType;

	@Length(max= 200,message="企业头像地址长度不能超过200")
	@ApiModelProperty("企业头像地址")
    private String companyUrl;

	@ApiModelProperty("是否官方认证")
    private Boolean isOfficial;

}



