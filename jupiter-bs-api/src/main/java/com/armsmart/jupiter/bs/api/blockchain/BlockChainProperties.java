package com.armsmart.jupiter.bs.api.blockchain;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 区块链配置
 *
 * @author wei.lin
 **/
@Data
@Component
@ConfigurationProperties(prefix = "block-chain")
public class BlockChainProperties {

    /**
     * topic
     */
    private Topic topic;
    /**
     * 区块链处理超时时间
     */
    private long timeout;

    @Data
    public static class Topic {
        /**
         * 鉴权topic
         */
        private String auth;

        /**
         * 鉴定topic
         */
        public String identify;
        /**
         * 交易topic
         */
        public String transaction;
        /**
         * 区块链一般操作topic
         */
        public String common;

        /**
         * 返回鉴权topic
         */
        public String authResult;
        /**
         * 返回鉴定topic
         */
        public String identifyResult;
        /**
         * 返回交易topic
         */
        public String transactionResult;
        /**
         * 返回区块链一般操作topic
         */
        public String commonResult;
        /**
         * 所有返回结果topic
         */
        private String allResult;
        /**
         * 消费组
         */
        private String platformToChainGroup;
        /**
         * 测试topic
         */
        private String demo;
        /**
         * 测试结果返回topic
         */
        private String demoResult;
    }
}
