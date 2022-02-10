package com.armsmart.jupiter.bs.api.entity;
import lombok.Data;

import java.util.List;


/**
 *ArtInfo entity
 * @author 苏礼刚
 **/
@Data
public class ArtInfo{
    /**
    *艺术品ID
    */
    private Long artId;

    /**
    *合约地址
    */
    private String contractAddr;

    /**
    *艺术品对应的NFCID
    */
    private String artNfcId;

    /**
    *名称
    */
    private String artName;

    /**
    *年代
    */
    private String artYear;

    /**
    *艺术品种类（1.书法、2.绘画、3.瓷器、 4.古玩、5.钱币、6.明星、7.俱乐部周边）
    */
    private Integer artType;

    /**
    *艺术品尺寸
    */
    private String artSize;

    /**
    *艺术品重量
    */
    private Integer artWeight;

    /**
    *艺术品成色，0.一般、1.良好、2.优
    */
    private Integer artCondition;

    /**
    *艺术品制造商
    */
    private String artManufacturer;

    /**
    *艺术品条码信息
    */
    private String artBarcode;

    /**
    *艺术品描述
    */
    private String artDesc;

    /**
    *作者
    */
    private String author;

    /**
    *用户ID
    */
    private Integer userId;

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

    /**
     * 艺术品照片列表
     */
    private List<ArtPicInfo> pics;


}




