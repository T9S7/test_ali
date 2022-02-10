package com.armsmart.jupiter.bs.api.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;


import javax.validation.constraints.NotNull;

/**
 * AppVersion详情DTO
 *
 * @author 苏礼刚
 **/
@Data
@ApiModel(description = "_response")
public class AppVersionDetail {

    @ApiModelProperty("主键ID")
    private java.lang.Integer id;

    @ApiModelProperty("平台 0 安卓，1 ios")
    private Integer platform;

    @ApiModelProperty("是否强制更新（0--否，1--是）")
    private Boolean isForceUpdate;

    @ApiModelProperty("版本code")
    private Integer versionCode;

    @ApiModelProperty("版本号")
    private String versionNum;

    @ApiModelProperty("版本描述")
    private String versionDes;

    @ApiModelProperty("程序下载地址")
    private String downloadUrl;

    @ApiModelProperty("createTime")
    private Long createTime;

    @ApiModelProperty("updateTime")
    private Long updateTime;

}



