package com.armsmart.jupiter.bs.api.blockchain;

import lombok.Getter;

/**
 * 区块链函数枚举
 *
 * @author wei.lin
 **/
@Getter
public enum BlockChainNftFunction {
    /**
     * 区块链函数
     */
    APPLY_CHECK("申请鉴权", "applyCheck"),
    RAND("随机数", "rand"),
    RANDTEST("随机数测试接口", "rand"),

    SET_ART_PUB_KEY("设置艺术品公钥", "setArtPubKey"),
    SET_USER_KEY("设置艺术家公钥", "setUserkey"),
    SET_ART_INFO("录入艺术品信息", "setArtInfo"),
    GET_ART_INFO("获取艺术品信息", "getArtInfo"),
    RESET_CONTRACT("重置艺术品合约", "resetContract"),
    GET_COUNT("鉴权记录查询", "getCount"),
    DEPLOY("发布", "deploy"),
    ACCESS("申请鉴权", "access"),
    MINER("申请鉴权", "miner"),
    MODIFYOWNER("变更所有者","modifyOwner"),
    INSTANCEDEPOLY("Jupiter合约发布","instanceDepoly"),
    ;

    String name;
    String func;

    BlockChainNftFunction(String name, String func) {
        this.name = name;
        this.func = func;
    }
}
