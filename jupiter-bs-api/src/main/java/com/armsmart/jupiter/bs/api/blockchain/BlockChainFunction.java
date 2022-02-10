package com.armsmart.jupiter.bs.api.blockchain;

import lombok.Getter;

/**
 * 区块链函数枚举
 *
 * @author wei.lin
 **/
@Getter
public enum BlockChainFunction {
    /**
     * 区块链函数
     */
    APPLY_CHECK("申请鉴权", "applyCheck"),
    RAND("随机数", "rand"),
    RANDTEST("随机数测试接口", "rand"),
    COIN_LOAD("鉴定录入", "setCoinInfo"),
    SET_COMPANY_KEY("企业公钥录入", "setCompanyKey"),
    SETS_USER_KEY("鉴定人录入人公钥录入", "setsUserkey"),
    LOAD_INFO_CHECK("鉴定人录入信息确认", "loadInfoCheck"),
    GET_COIN_INFO("古币信息查询", "getCoinInfo"),
    GET_TICATE_INFO("鉴定人信息查询", "getTicateInfo"),
    GET_COUNT("鉴权记录查询", "getCount"),
    GET_CHECK_STATUS("申请鉴权", "getCheckStatus"),
    DEPLOY("发布", "deploy"),
    ACCESS("申请鉴权", "access"),
    MINER("申请鉴权", "miner");

    String name;
    String func;

    BlockChainFunction(String name, String func) {
        this.name = name;
        this.func = func;
    }
}
