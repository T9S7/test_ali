package com.armsmart.jupiter.bs.api.dto.response;

import com.armsmart.jupiter.bs.api.entity.ThingPicInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;


import javax.validation.constraints.NotNull;
import java.util.List;

/**
 *ThingInfo详情DTO
 * @author 苏礼刚
 **/
@Data
@ApiModel(description = "_response")
public class ThingInfoDetail {
    @ApiModelProperty("ID")
    private Long id;

    @ApiModelProperty("合约地址")
    private String contractAddr;

    @ApiModelProperty("名称")
    private String artName;

    @ApiModelProperty("年代")
    private String artYear;

    @ApiModelProperty("作者")
    private String author;

    @ApiModelProperty("描述")
    private String thingDesc;

    @ApiModelProperty("尺寸")
    private String thingSize;

    @ApiModelProperty("重量")
    private Integer thingWeight;

    @ApiModelProperty("资质")
    private String thingCondition;

    @ApiModelProperty("成分")
    private String thingElement;

    @ApiModelProperty("分类")
    private Integer thingType;

    @ApiModelProperty("用户ID")
    private Integer userId;

    @ApiModelProperty("上传状态")
    private Boolean uploadStatus;

    @ApiModelProperty("当前价格")
    private Long currentPrice;

    @ApiModelProperty("当前状态 -1 非卖展示，99 私密隐藏，1 在售，2 已出售，9已下架")
    private Integer currentState;

    @ApiModelProperty("创建时间")
    private Long createTime;

    @ApiModelProperty("修改时间")
    private Long updateTime;

    @ApiModelProperty("是否删除")
    private Boolean isDel;

    @ApiModelProperty("图片地址")
    private List<ThingPicInfo> pics;

    @ApiModelProperty("是否官方发布")
    private Boolean isOfficial;

    @ApiModelProperty("封面图")
    private String coverPic;

    @ApiModelProperty("长图")
    private String longPic;


}



