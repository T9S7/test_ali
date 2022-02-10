package com.armsmart.jupiter.bs.api.entity;
import lombok.Data;


/**
 *UserCollect entity
 * @author 苏礼刚
 **/
@Data
public class UserCollect{
    /**
    *序号
    */
    private Long id;

    /**
    *收藏物品id
    */
    private Long thingId;

    /**
    *收藏用户id
    */
    private Long userId;

    /**
    *创建时间
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




