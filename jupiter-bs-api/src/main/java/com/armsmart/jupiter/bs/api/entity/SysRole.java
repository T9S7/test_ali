package com.armsmart.jupiter.bs.api.entity;
import lombok.Data;


/**
 *SysRole entity
 * @author wei.lin
 **/
@Data
public class SysRole{
    /**
    *角色ID
    */
    private Integer roleId;

    /**
    *角色名称
    */
    private String roleName;

    /**
    *角色类型（1：超级管理员，2管理人员，3运维人员，4操作人员，5客服人员）
    */
    private Integer roleType;

    /**
    *角色描述
    */
    private String roleDesc;

    /**
    *角色级别（值越小级别越高，低级别不能操作高级别）
    */
    private Integer roleLevel;

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




