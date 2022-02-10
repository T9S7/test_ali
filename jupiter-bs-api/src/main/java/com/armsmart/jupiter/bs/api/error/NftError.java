package com.armsmart.jupiter.bs.api.error;

import com.armsmart.common.api.ErrorCode;


/**
 * NFT响应码定义
 *
 * @author wei.lin
 */
public enum NftError implements ErrorCode {

    STORE_NOT_EXIST(50001, "店铺不存在，请先认证"),

    IN_STORE_PRICE_MUST_SET(50002, "陈列到店铺时，请设置艺术品价格"),
    PUT_ON_TIME_MUST_SET(50003, "陈列到店铺时，请设置上架时间"),
    PUT_ON_TIME_MUST_GT_CURRENT_TIME(50004, "上架时间必须大于当前时间"),
    PUT_ON_REPEAT(50005, "请勿重复上架"),
    ART_NOT_EXIST_IN_STORE(50006, "店铺中没有此艺术品"),
    STORE_EXIST(50007, "店铺已存在，请勿重复添加"),
    ART_NOT_EXIST(50008, "艺术品不存在"),
    REAL_PERSON_TOKEN_ABNORMAL(50009,"获取实人认证TOKEN异常"),
    REAL_PERSON_ABNORMAL(50010,"获取实人认证结果异常"),
    REAL_PERSON_CERT_FILE(50011,"身份证对应实人认证记录不存在"),
    REAL_PERSON_PUBLIC_KEY_FILE(50012,"公钥信息不正确"),
    REAL_PERSON_USER_EXIST_FILE(50013,"当前身份证已和其他账户完成绑定，请先解绑再重新绑定！"),
    ART_TITLE_NOT_EXIST_FILE(50014,"当前用户实名认证不全，请先完成实名认证并绑定"),
    ART_TITLE_USER_NOT_EXIST_FILE(50015,"当前资质认证对应用户不合法"),
    ART_TITLE_USER_NOT_CERT_FILE(50016,"当前资质认证申请人未实名认证"),
    LOAD_ON_REPEAT(50017, "该芯片已录入信息，请勿重复录入"),
    NFC_TYPE_NO_EXIST(50018, "鉴权类型不存在"),

    ;


    int code;
    String msg;

    NftError(int code, String msg) {
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
