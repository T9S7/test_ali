package com.armsmart.jupiter.bs.api.cache;

import com.armsmart.jupiter.bs.api.cache.base.BaseRedisCache;
import com.armsmart.jupiter.bs.api.cache.constants.CacheConstant;
import lombok.extern.slf4j.Slf4j;
import com.armsmart.jupiter.bs.api.entity.OrderInfo;
import org.springframework.stereotype.Service;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class OrderSendCacheService extends BaseRedisCache<String> {


    public void set(OrderInfo orderInfo) {
        log.info("订单{}待发货！", orderInfo.getOrderId());
        redisTemplate.opsForValue().set(buildCacheKey(CacheConstant.ORDER_SEND_CACHE, String.valueOf(orderInfo.getOrderId())), String.valueOf(orderInfo.getOrderId()), getTtl(orderInfo.getSendOutDeadline()), TimeUnit.MILLISECONDS);
    }

    public void delete(Long orderId) {
        String key = buildCacheKey(CacheConstant.ORDER_SEND_CACHE, String.valueOf(orderId));
        redisTemplate.delete(key);
        log.info("删除订单待发货缓存信息！orderId={}", key);
    }


    protected long getTtl(Long time) {
        return time - System.currentTimeMillis();
    }

}
