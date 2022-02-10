package com.armsmart.jupiter.bs.api.entity;
import lombok.Data;


/**
 *ProductInfo entity
 * @author 林伟
 **/
@Data
public class ProductInfo{
    /**
    *产品ID
    */
    private Long id;

    /**
    *产品名称
    */
    private String name;

    /**
    *一级类型
    */
    private Integer typeLevelOne;

    /**
    *二级类型
    */
    private Integer typeLevelTwo;

    /**
    *作者
    */
    private String author;

    /**
    *拥有者
    */
    private String owner;

    /**
    *序号
    */
    private String serialNum;

    /**
    *介绍
    */
    private String description;

    /**
    *规格
    */
    private String specs;

    /**
    *库存
    */
    private Integer stock;

    /**
    *上架状态
    */
    private Boolean putOn;

    /**
    *审核状态（0：待审核、1：审核中、2：已驳回、3：重新审核）
    */
    private Integer auditStatus;

    /**
    *审核意见
    */
    private String auditComments;

    /**
    *上架时间
    */
    private Long putOnTime;

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




