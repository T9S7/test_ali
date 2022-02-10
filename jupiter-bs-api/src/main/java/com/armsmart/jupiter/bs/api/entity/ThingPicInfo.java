package com.armsmart.jupiter.bs.api.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * ThingPicInfo entity
 *
 * @author 苏礼刚
 **/
@Data
public class ThingPicInfo {
    /**
     * 主键ID
     */
    private Long id;

    /**
     * 物品ID
     */
    private Long thingId;

    /**
     * 图片地址
     */
    private String picUrl;

    /**
     * 图片md5码
     */
    private String picMd5;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 修改时间
     */
    private Long updateTime;

    /**
     * 是否删除
     */
    private Boolean isDel;
    /**
     * 类型 0：图片；1：视频
     */
    @ApiModelProperty("类型 0：图片；1：视频")
    private Integer type;

}




