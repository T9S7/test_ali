package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * AppVersion修改DTO
 *
 * @author wei.lin
 **/
@Data
@ApiModel(description = "_request")
public class AppVersionUpdateParam {


    @NotNull(message = "id不能为空")
    @ApiModelProperty(value = "id", required = true)
    private Integer id;

    @Range(min = 0, max = 1, message = "平台仅支持 0 安卓，1 IOS")
    @NotNull(message = "平台 0 安卓，1 ios不能为空")
    @ApiModelProperty(value = "平台 0 安卓，1 ios", required = true)
    private Integer platform;

    @NotNull(message = "是否强制更新（0--否，1--是）不能为空")
    @ApiModelProperty(value = "是否强制更新（0--否，1--是）", required = true)
    private Boolean isForceUpdate;

    @NotNull(message = "版本code不能为空")
    @ApiModelProperty(value = "版本code", required = true)
    private Integer versionCode;

    @NotBlank(message = "版本号不能为空")
    @Length(max = 20, message = "版本号长度不能超过20")
    @ApiModelProperty(value = "版本号", required = true)
    private String versionNum;

    @Length(max = 2000, message = "版本描述长度不能超过2000")
    @ApiModelProperty("版本描述")
    private String versionDes;

    @Length(max = 255, message = "程序下载地址长度不能超过255")
    @ApiModelProperty("程序下载地址")
    private String downloadUrl;


}



