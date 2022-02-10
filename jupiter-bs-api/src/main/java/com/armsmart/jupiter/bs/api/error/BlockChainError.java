package com.armsmart.jupiter.bs.api.error;

import com.armsmart.common.api.ErrorCode;


/**
 * 业务响应码定义
 *
 * @author wei.lin
 */
public enum BlockChainError implements ErrorCode {

    ORDER_DETAIL_IS_EMPTY(30001, "送评物品信息不能为空"),
    COIN_PHOTO_URL_INVALID(30002, "高清图片地址无效"),
    COIN_LOAD_MD5_NOT_MATCH(30003, "MD5不匹配"),
    BLOCK_CHAIN_TIMEOUT(30004, "区块链服务超时"),
    INVALID_INPUT_USER(30005, "无效的录入人"),
    INVALID_TICATE_USER(30006, "无效的鉴定人"),
    PUB_KEY_NOT_INIT(30007, "鉴定人或录入人公钥未初始化"),
    COIN_ID_NOT_EXIST(30008, "鉴定编号不存在"),
    COIN_SIGN_UNAUTHORIZED(30009, "签名未经授权"),
    PUBLISH_UNAUTHORIZED(30010, "发布未经授权"),
    ;


    int code;
    String msg;

    BlockChainError(int code, String msg) {
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
