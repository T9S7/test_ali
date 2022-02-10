package com.armsmart.jupiter.bs.api.entity;
import lombok.Data;


/**
 *ContractInfo entity
 * @author 苏礼刚
 **/
@Data
public class ContractInfo{
    /**
    *id
    */
    private Integer id;

    /**
    *节点主机host
    */
    private String nodeHost;

    /**
    *合约名称
    */
    private String contractName;

    /**
    *合约地址
    */
    private String contractAddress;

    /**
    *合约所有者
    */
    private String contractOwner;

    /**
    *创建时间
    */
    private java.util.Date createTime;

    /**
    *更新时间
    */
    private java.util.Date updateTime;

    /**
    *合约获取随机数的seed
    */
    private Long randomSeed;

    /**
    *通道名称
    */
    private String channelName;

    /**
    *链码id
    */
    private String chainCodeId;

    /**
    *是否是华夏卡
    */
    private Integer isHuaxiaCard;

    /**
    *是否已使用
    */
    private Boolean isUsed;


}




