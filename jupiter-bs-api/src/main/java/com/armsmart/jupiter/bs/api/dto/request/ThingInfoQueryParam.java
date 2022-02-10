package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;


/**
 * ThingInfo 查询参数
 *
 * @author 苏礼刚
 **/
@Data
@EqualsAndHashCode(callSuper = false)
public class ThingInfoQueryParam extends PageQueryParam {
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

    @ApiModelProperty("上传用户ID")
    private Integer uploaderId;

    @ApiModelProperty("上传状态")
    private Boolean uploadStatus;

    @ApiModelProperty("当前价格")
    private Long currentPrice;

    @ApiModelProperty("当前状态")
    private Integer currentState;

    @ApiModelProperty("创建时间")
    private Long createTime;

    @ApiModelProperty("修改时间")
    private Long updateTime;

    @ApiModelProperty("是否删除")
    private Boolean isDel;

    @ApiModelProperty("是否官方")
    private Boolean isOfficial;

    @ApiModelProperty("当前状态集合")
    private List<Integer> currentStates;
}




