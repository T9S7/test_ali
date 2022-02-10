package com.armsmart.jupiter.bs.api.entity;
import lombok.Data;


/**
 *AppTutorialVideo entity
 * @author wei.lin
 **/
@Data
public class AppTutorialVideo{
    /**
    *主键ID
    */
    private Integer id;

    /**
    *视频地址
    */
    private String videoUrl;

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




