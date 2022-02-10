package com.armsmart.jupiter.bs.api.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;


import javax.validation.constraints.NotNull;

/**
 *CompanyInfo详情DTO
 * @author 苏礼刚
 **/
@Data
@ApiModel(description = "_response")
public class CompanyInfoDetail {
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



