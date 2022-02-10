package com.armsmart.jupiter.bs.api.entity;
import lombok.Data;


/**
 *StoreArtInfo entity
 * @author wei.lin
 **/
@Data
public class StoreArtInfo{
    /**
    *主键ID
    */
    private Long id;

    /**
    *店铺ID
    */
    private Long storeId;

    /**
    *艺术品ID
    */
    private Long artId;

    /**
    *艺术品价格（分）
    */
    private Long artPrice;

    /**
    *是否上架
    */
    private Boolean putOn;

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




