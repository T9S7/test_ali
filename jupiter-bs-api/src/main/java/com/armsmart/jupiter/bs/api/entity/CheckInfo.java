package com.armsmart.jupiter.bs.api.entity;
import lombok.Data;


/**
 *CheckInfo entity
 * @author 苏礼刚
 **/
@Data
public class CheckInfo{
    /**
    *id
    */
    private Integer id;

    /**
    *contractAddr
    */
    private String contractAddr;

    /**
    *物品id
    */
    private Long thingId;

    /**
    *校验状态(0-失败，1-成功)
    */
    private Integer checkType;

    /**
    *失败原因
    */
    private String fileInfo;

    /**
    *用户id
    */
    private Integer userId;

    /**
    *校验时间
    */
    private Long createTime;

    /**
    *更新时间
    */
    private Long updateTime;

    /**
    *是否删除
    */
    private Boolean isDel;


}




