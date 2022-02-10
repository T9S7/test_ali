package com.armsmart.jupiter.bs.api.entity;
import lombok.Data;


/**
 *ProductPic entity
 * @author 林伟
 **/
@Data
public class ProductPic{
    /**
    *主键
    */
    private Long id;

    /**
    *产品ID
    */
    private Long productId;

    /**
    *图片地址
    */
    private String picUrl;

    /**
    *图片下标
    */
    private Integer picIndex;

    /**
    *图片MD5码
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




