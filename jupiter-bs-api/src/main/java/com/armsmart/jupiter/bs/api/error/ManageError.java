
package com.armsmart.jupiter.bs.api.error;


import com.armsmart.common.api.ErrorCode;

/**
 * 管理端响应码定义
 *
 * @author wei.lin
 */
public enum ManageError implements ErrorCode {
    WRONG_USERNAME_OR_PWD(10001, "用户名或密码错误"),
    USER_IS_DISABLED(10002, "该用户已被禁用"),
    USER_IS_NOT_EXIST(10003, "用户不存在"),
    USER_OLD_PWD_NOT_MATCH(10004, "旧密码错误"),
    USER_PWD_CHANGE_ERROR(10005, "密码修改失败"),
    USERNAME_IS_EXIST(10006, "该用户名已注册"),
    TELEPHONE_IS_EXIST(10007, "该手机号已注册"),
    EMAIL_IS_EXIST(10008, "该邮箱已注册"),

    ROLE_NOT_EXIST_OR_DEL(10101, "角色不存在或已删除"),
    ROLE_NAME_IS_EXIST(10102, "角色名称已存在"),
    ROLE_TYPE_IS_USED(10103, "该类型的角色已存在"),
    ;


    int code;
    String msg;

    ManageError(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }


    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.msg;
    }


}
