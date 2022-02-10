package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.util.List;

/**
 * ThingInfo添加DTO
 *
 * @author 苏礼刚
 **/
@Data
@ApiModel(description = "_request")
public class ThingNfcAddParam {


    @NotBlank(message = "名称不能为空")
    @Length(max = 64, message = "名称长度不能超过64")
    @ApiModelProperty(value = "名称", required = true)
    private String artName;

    @NotBlank(message = "年代不能为空")
    @Length(max = 64, message = "年代长度不能超过64")
    @ApiModelProperty(value = "年代", required = true)
    private String artYear;

    @NotBlank(message = "作者不能为空")
    @Length(max = 64, message = "作者长度不能超过64")
    @ApiModelProperty(value = "作者", required = true)
    private String author;

    @NotBlank(message = "描述不能为空")
    @Length(max = 255, message = "描述长度不能超过255")
    @ApiModelProperty(value = "描述", required = true)
    private String thingDesc;

    @ApiModelProperty("分类")
    private Integer thingType;

    @ApiModelProperty(value = "用户ID")
    private Integer userId;

    @NotNull(message = "图片地址不能为空")
    @ApiModelProperty(value = "图片地址不能空", required = true)
    private List<ThingPicInfoAddParam> pics;


    @ApiModelProperty(value = "价格")
    private Integer currentPrice;

    @ApiModelProperty("封面图")
    private String coverPic;

    @ApiModelProperty("长图（详情）")
    private String longPic;
}



