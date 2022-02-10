package com.armsmart.jupiter.bs.api.entity;
import lombok.Data;


/**
 *ArtPicInfo entity
 * @author wei.lin
 **/
@Data
public class ArtPicInfo{
    /**
    *主键ID
    */
    private Long id;

    /**
    *艺术品ID
    */
    private Long artId;

    /**
    *图片地址
    */
    private String picUrl;

    /**
    *图片md5码
    */
    private String picMd5;

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




