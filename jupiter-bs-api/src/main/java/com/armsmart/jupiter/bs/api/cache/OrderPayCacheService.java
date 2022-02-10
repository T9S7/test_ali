package com.armsmart.jupiter.bs.api.cache;

import com.armsmart.jupiter.bs.api.cache.base.BaseRedisCache;
import com.armsmart.jupiter.bs.api.cache.constants.CacheConstant;
import com.armsmart.jupiter.bs.api.entity.OrderInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * 订单待支付缓存
 *
 * @author wei.lin
 **/
@Slf4j
@Service
public class OrderPayCacheService extends BaseRedisCache<String> {

    public void set(OrderInfo orderInfo) {
        log.info("订单{}待支付！{}", orderInfo.getOrderId());
        redisTemplate.opsForValue().set(buildCacheKey(CacheConstant.ORDER_PAY_CACHE, String.valueOf(orderInfo.getOrderId())), String.valueOf(orderInfo.getOrderId()), getTtl(orderInfo.getPayDeadline()), TimeUnit.MILLISECONDS);
    }

    public void delete(Long orderId) {
        String key = buildCacheKey(CacheConstant.ORDER_PAY_CACHE, String.valueOf(orderId));
        redisTemplate.delete(key);
        log.info("删除订单待支付缓存信息！orderId={}", key);
    }


    protected long getTtl(Long time) {
        return time - System.currentTimeMillis();
    }


}
