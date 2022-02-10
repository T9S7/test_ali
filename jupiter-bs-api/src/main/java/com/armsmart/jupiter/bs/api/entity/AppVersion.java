package com.armsmart.jupiter.bs.api.entity;

import lombok.Data;


/**
 * AppVersion entity
 *
 * @author 苏礼刚
 **/
@Data
public class AppVersion {
    /**
     * id
     */
    private Integer id;

    /**
     * 平台 0 安卓，1 ios
     */
    private Integer platform;

    /**
     * 是否强制更新（0--否，1--是）
     */
    private Boolean isForceUpdate;

    /**
     * 版本code
     */
    private Integer versionCode;

    /**
     * 版本号
     */
    private String versionNum;

    /**
     * 版本描述
     */
    private String versionDes;
    /**
     * 程序下载地址
     */
    private String downloadUrl;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 更新时间
     */
    private Long updateTime;

    /**
     * 是否删除
     */
    private Boolean isDel;


}




