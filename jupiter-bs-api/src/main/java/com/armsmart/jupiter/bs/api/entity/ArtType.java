package com.armsmart.jupiter.bs.api.entity;
import lombok.Data;


/**
 *ArtType entity
 * @author 苏礼刚
 **/
@Data
public class ArtType{
    /**
    *序号
    */
    private Integer id;

    /**
    *级别
    */
    private Integer lever;

    /**
    *类别名称
    */
    private String typeName;

    /**
    *父id
    */
    private Integer parentId;

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




