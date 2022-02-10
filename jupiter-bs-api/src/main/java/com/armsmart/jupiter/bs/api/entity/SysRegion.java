package com.armsmart.jupiter.bs.api.entity;
import lombok.Data;


/**
 *SysRegion entity
 * @author 苏礼刚
 **/
@Data
public class SysRegion {
    /**
    *主键
    */
    private Integer id;

    /**
    *区域名称
    */
    private String regionName;

    /**
    *父节点ID
    */
    private Integer regionParent;

    /**
    *纬度
    */
    private String latitude;

    /**
    *精度
    */
    private String longitude;


}




