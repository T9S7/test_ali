package com.armsmart.jupiter.bs.api.cache;

import com.armsmart.jupiter.bs.api.cache.base.BaseRedisCache;
import com.armsmart.jupiter.bs.api.cache.constants.CacheConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.concurrent.TimeUnit;

/**
 * 竞拍信息缓存
 *
 * @author wei.lin
 **/
@Slf4j
@Service
public class OrderLockCacheService extends BaseRedisCache<String> {

    /**
     * 根据拍卖ID获取锁
     *
     * @param thingId
     * @return boolean
     */
    public boolean lock(String thingId) {
        String cacheKey = buildCacheKey(CacheConstant.ORDER_LOCK_CACHE, thingId);
        return redisTemplate.opsForValue().setIfAbsent(cacheKey, thingId, 30, TimeUnit.SECONDS);
    }

    /**
     * 根据拍卖ID释放锁
     *
     * @param thingId
     * @return boolean
     */
    public boolean unlock(String thingId) {
        String cacheKey = buildCacheKey(CacheConstant.ORDER_LOCK_CACHE, thingId);
        return redisTemplate.delete(cacheKey);
    }
}
