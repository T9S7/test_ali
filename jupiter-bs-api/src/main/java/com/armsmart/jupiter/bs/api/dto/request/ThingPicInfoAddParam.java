package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;


import javax.validation.constraints.NotNull;

/**
 * ThingPicInfo添加DTO
 *
 * @author 苏礼刚
 **/
@Data
@ApiModel(description = "_request")
public class ThingPicInfoAddParam {


    @ApiModelProperty(value = "物品ID", hidden = true)
    private Long thingId;

    @NotBlank(message = "图片地址不能为空")
    @Length(max = 128, message = "图片地址长度不能超过128")
    @ApiModelProperty(value = "图片地址", required = true)
    private String picUrl;

    @NotBlank(message = "图片md5码不能为空")
    @Length(max = 36, message = "图片md5码长度不能超过36")
    @ApiModelProperty(value = "图片md5码", required = true)
    private String picMd5;

    @ApiModelProperty("类型 0：图片；1：视频")
    private Integer type = 0;


}



