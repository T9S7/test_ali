package com.armsmart.jupiter.bs.api.entity;
import lombok.Data;


/**
 *SysResource entity
 * @author wei.lin
 **/
@Data
public class SysResource{
    /**
    *资源ID
    */
    private Integer resourceId;

    /**
    *资源名称
    */
    private String resourceName;

    /**
    *资源类型（1：菜单；2：按钮；3：查看）
    */
    private Integer resourceType;

    /**
    *访问路径
    */
    private String resourcePath;

    /**
    *资源唯一标识
    */
    private String resourceKey;

    /**
    *资源排序值
    */
    private Integer resourceSort;

    /**
    *父ID
    */
    private Integer parentId;

    /**
    *启用状态
    */
    private Boolean isEnable;

    /**
    *是否删除
    */
    private Boolean isDel;

    /**
    *创建时间
    */
    private Long createTime;

    /**
    *修改时间
    */
    private Long updateTime;


}




