package com.armsmart.jupiter.bs.api.error;

import com.armsmart.common.api.ErrorCode;


/**
 * 非管理端业务响应码定义
 *
 * @author wei.lin
 */
public enum BusinessError implements ErrorCode {

    USER_NOT_EXIST(20000, "用户不存在"),
    WRONG_USERNAME_OR_PWD(20001, "账号或密码错误"),
    MOBILE_WAS_REGISTERED(20002, "该手机号已注册"),
    SMS_SEND_FAILED(20003, "验证码发送失败"),
    MOBILE_IS_VALID(20004, "手机号格式错误"),
    SMS_SEND_BUSY(20005, "验证码发送太频繁"),
    VERIFY_CODE_INVALID(20006, "验证码错误"),
    AUTH_EXPIRE_OR_FAILED(20007, "验证码认证失败"),
    MOBILE_NOT_REGISTER(20008, "手机号未注册"),
    USER_IS_DISABLED(20009, "账号已禁用"),
    USER_MOBILE_REPEATED(20010, "不能与当前手机号相同"),
    USER_MOBILE_VERIFY_FAILED(20011, "手机号认证登录失败"),
    USER_NOT_SET_DEFAULT_ADDR(20020, "用户未设置默认地址"),
    USER_FANS_NOT_EXIST(20031,"数据信息异常"),
    USER_EX_MOBILE_NOT_SAME(20032,"当前账户绑定原手机号不正确"),
    USER_MOBILE_EXIST(20034,"手机号已绑定，请更换其他手机号"),
    USER_FOCUS_EXIST(20035,"用户关注已存在，请勿重复关注"),
    USER_FOCUS_NOT_MYSELF(20036,"用户不可以关注自己！"),
    ;

    int code;
    String msg;

    BusinessError(int code, String msg) {
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
