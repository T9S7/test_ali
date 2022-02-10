package com.armsmart.jupiter.bs.api.entity;

import lombok.Data;

import java.util.List;


/**
 * ThingInfo entity
 *
 * @author 苏礼刚
 **/
@Data
public class ThingInfo {
    /**
     * ID
     */
    private Long id;

    /**
     * 合约地址
     */
    private String contractAddr;

    /**
     * 名称
     */
    private String artName;

    /**
     * 年代
     */
    private String artYear;

    /**
     * 作者
     */
    private String author;

    /**
     * 描述
     */
    private String thingDesc;

    /**
     * 尺寸
     */
    private String thingSize;

    /**
     * 重量
     */
    private Integer thingWeight;

    /**
     * 资质
     */
    private String thingCondition;

    /**
     * 成分
     */
    private String thingElement;

    /**
     * 分类 -1， 非卖展示 ，99私密隐藏
     */
    private Integer thingType;

    /**
     * 上传用户ID
     */
    private Integer uploaderId;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 上传状态
     */
    private Boolean uploadStatus;

    /**
     * 当前价格
     */
    private Long currentPrice;

    /**
     * 当前状态(-1， 非卖展示，0未发布，1售卖中，2已出售，3赠送中，9已下架，99私密隐藏)
     */
    private Integer currentState;

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
     * 是否官方
     */
    private Boolean isOfficial;

    /**
    *封面图
    */
    private String coverPic;

    /**
     *长图
     */
    private String longPic;

    private List<ThingPicInfo> pics;

}




