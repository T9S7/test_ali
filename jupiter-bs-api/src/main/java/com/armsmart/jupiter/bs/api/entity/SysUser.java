package com.armsmart.jupiter.bs.api.entity;

import lombok.Data;


/**
 * SysUser entity
 *
 * @author wei.lin
 **/
@Data
public class SysUser {
    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 用户账号
     */
    private String username;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 性别（1：男；2女：0：未知）
     */
    private Integer gender;

    /**
     * 联系电话
     */
    private String telephone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 启用状态
     */
    private Boolean isEnable;

    /**
     * 角色ID
     */
    private Integer roleId;

    /**
     * 是否删除
     */
    private Boolean isDel;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 修改时间
     */
    private Long updateTime;

    /**
     * 最后登录时间
     */
    private Long lastLoginTime;

    /**
     * 是否需要修改密码
     */
    private Boolean needChangePwd;


    /**
     * 用户角色
     */
    private SysRole sysRole;

}




