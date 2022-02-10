package com.armsmart.jupiter.bs.api.cache;

import com.armsmart.jupiter.bs.api.cache.base.BaseRedisCache;
import com.armsmart.jupiter.bs.api.cache.constants.CacheConstant;
import com.armsmart.jupiter.bs.api.entity.BidInfo;
import com.armsmart.jupiter.bs.api.entity.OrderInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * 拍卖待下订单缓存
 *
 * @author wei.lin
 **/
@Slf4j
@Service
public class OrderDeadlineCacheService extends BaseRedisCache<BidInfo> {

    public void set(BidInfo bidInfo) {
        log.info("拍卖{}待下单！", bidInfo.getSellId());
        redisTemplate.opsForValue().set(buildCacheKey(CacheConstant.BID_ORDER_CACHE, String.valueOf(bidInfo.getBidId())), bidInfo, getTtl(bidInfo.getOrderDeadline()), TimeUnit.MILLISECONDS);
    }

    public BidInfo get(Long bidId) {
        return redisTemplate.opsForValue().get(buildCacheKey(CacheConstant.BID_ORDER_CACHE, String.valueOf(bidId)));
    }

    public void delete(Long bidId) {
        String key = buildCacheKey(CacheConstant.BID_ORDER_CACHE, String.valueOf(bidId));
        redisTemplate.delete(key);
        log.info("删除拍卖待下单缓存信息！key={}", key);
    }


    protected long getTtl(Long time) {
        return time - System.currentTimeMillis();
    }


}
