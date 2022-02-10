package com.armsmart.jupiter.bs.api.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 *SysRolePermission entity
 * @author wei.lin
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysRolePermission{
    /**
    *id
    */
    private Integer id;

    /**
    *角色ID
    */
    private Integer roleId;

    /**
    *资源ID
    */
    private Integer resourceId;

    /**
    *创建时间
    */
    private Long createTime;


}




