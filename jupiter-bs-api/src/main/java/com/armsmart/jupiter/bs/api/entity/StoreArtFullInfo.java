package com.armsmart.jupiter.bs.api.entity;

import lombok.Data;

import java.util.List;


/**
 * StoreArtInfo entity
 *
 * @author wei.lin
 **/
@Data
public class StoreArtFullInfo extends StoreArtInfo {

    /**
     * 合约地址
     */
    private String contractAddr;

    /**
     * 名称
     */
    private String artName;

    /**
     * 年代
     */
    private String artYear;

    /**
     * 艺术品种类（1.书法、2.绘画、3.瓷器、 4.古玩、5.钱币、6.明星、7.俱乐部周边）
     */
    private Integer artType;

    /**
     * 艺术品描述
     */
    private String artDesc;

    /**
     * 艺术品卡片id
     */
    private String artNfcId;

    /**
     * 作者
     */
    private String author;

    /**
     * 艺术品图片信息
     */
    private List<ArtPicInfo> pics;

    /**
     * 用户ID
     */
    private Integer userId;


}




