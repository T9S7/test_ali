package com.armsmart.jupiter.bs.api.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;


import javax.validation.constraints.NotNull;

/**
 *ThingPicInfo详情DTO
 * @author 苏礼刚
 **/
@Data
@ApiModel(description = "_response")
public class ThingPicInfoDetail {
    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("物品ID")
    private Long thingId;

    @ApiModelProperty("图片地址")
    private String picUrl;

    @ApiModelProperty("图片md5码")
    private String picMd5;

    @ApiModelProperty("创建时间")
    private Long createTime;

    @ApiModelProperty("修改时间")
    private Long updateTime;

    @ApiModelProperty("是否删除")
    private Boolean isDel;

    @ApiModelProperty("类型 0：图片；1：视频")
    private Integer type;

}



