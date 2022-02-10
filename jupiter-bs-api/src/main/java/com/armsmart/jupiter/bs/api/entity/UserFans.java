package com.armsmart.jupiter.bs.api.entity;
import lombok.Data;


/**
 *UserFans entity
 * @author 苏礼刚
 **/
@Data
public class UserFans{
    /**
    *序号
    */
    private Long id;

    /**
    *用户id
    */
    private Long userId;

    /**
    *粉丝id
    */
    private Long focusUserId;

    /**
    *1 粉丝 2互相关注
    */
    private Integer focusState;

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




