package com.armsmart.jupiter.bs.api.dto.response;

import com.armsmart.jupiter.bs.api.entity.ThingPicInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * ThingInfo详情DTO
 *
 * @author 苏礼刚
 **/
@Data
@ApiModel(description = "_response")
public class ThingShortInfo {

    @ApiModelProperty("物品id")
    private Long thingId;

    @ApiModelProperty("物品名称")
    private String artName;

    @ApiModelProperty("发布类型 1 一口价；2 拍卖")
    private Integer sellType = 1;

    @ApiModelProperty("用户ID")
    private Integer userId;

    @ApiModelProperty("拍卖状态 0 拍卖前  1 拍卖中")
    private Integer auctionState;

    @ApiModelProperty("拍卖结束时间")
    private Long auctionTime;

    @ApiModelProperty("当前价格")
    private Long currentPrice;


    @ApiModelProperty("当前状态 -1 非卖展示，99 私密隐藏，1 在售，2 已出售，9已下架")
    private Integer currentState;

    @ApiModelProperty("创建时间")
    private Long createTime;

    @ApiModelProperty("修改时间")
    private Long updateTime;

    @ApiModelProperty("图片地址")
    private List<ThingPicInfo> pics;

    @ApiModelProperty("是否官方发布")
    private Boolean isOfficial;

    @ApiModelProperty("封面图")
    private String coverPic;

    @ApiModelProperty("长图")
    private String longPic;


}



