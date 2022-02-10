package com.armsmart.jupiter.bs.api.entity;
import lombok.Data;


/**
 *ProductVideo entity
 * @author 林伟
 **/
@Data
public class ProductVideo{
    /**
    *主键
    */
    private Long id;

    /**
    *产品ID
    */
    private Long productId;

    /**
    *视频地址
    */
    private String videoUrl;

    /**
    *视频下标
    */
    private Integer videoIndex;

    /**
    *创建时间
    */
    private Long createTime;

    /**
    *修改时间
    */
    private Long updateTime;

    /**
    *是否删除
    */
    private Boolean isDel;


}




