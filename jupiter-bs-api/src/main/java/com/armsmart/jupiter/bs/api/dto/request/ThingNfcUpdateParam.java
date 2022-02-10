package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * ThingInfo添加DTO
 *
 * @author 苏礼刚
 **/
@Data
@ApiModel(description = "_request")
public class ThingNfcUpdateParam {

    @NotNull(message = "ID不能为空")
    @ApiModelProperty(value = "ID", required = true)
    private Long id;

    @Length(max = 64, message = "名称长度不能超过64")
    @ApiModelProperty(value = "名称")
    private String artName;

    @Length(max = 64, message = "年代长度不能超过64")
    @ApiModelProperty(value = "年代")
    private String artYear;

    @Length(max = 64, message = "作者长度不能超过64")
    @ApiModelProperty(value = "作者")
    private String author;

    @Length(max = 255, message = "描述长度不能超过255")
    @ApiModelProperty(value = "描述")
    private String thingDesc;

    @ApiModelProperty("分类")
    private Integer thingType;

    @ApiModelProperty(value = "用户ID")
    private Integer userId;

    @ApiModelProperty(value = "图片地址不能空")
    private List<ThingPicInfoAddParam> pics;


    @ApiModelProperty(value = "价格")
    private Integer currentPrice;

    @ApiModelProperty("封面图")
    private String coverPic;

    @ApiModelProperty("长图（详情）")
    private String longPic;
}



