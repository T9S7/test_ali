package com.armsmart.jupiter.bs.api.cache.constants;

/**
 * 缓存常量
 *
 * @author wei.lin
 **/
public interface CacheConstant {
    /**
     * 获取锁重试最大次数
     */
    int LOCK_MAX_TRY_COUNT = 10;

    String AUCTION_BEGIN_CACHE = "auction:begin:";
    String AUCTION_END_CACHE = "auction:end:";

    String AUTH_CODE_CACHE = "auth:code:";
    String BID_LOCK_CACHE = "bid:lock:";
    String BID_ORDER_CACHE = "bid:order:";
    String ORDER_LOCK_CACHE = "order:lock:";
    String ORDER_PAY_CACHE = "order:pay:";
    String ORDER_SEND_CACHE = "order:send:";
}
