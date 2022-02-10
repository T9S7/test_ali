package com.armsmart.jupiter.bs.api.cache;

import com.armsmart.common.utils.JsonUtil;
import com.armsmart.jupiter.bs.api.cache.base.BaseRedisCache;
import com.armsmart.jupiter.bs.api.cache.constants.CacheConstant;
import com.armsmart.jupiter.bs.api.cache.model.ThingSellInfoCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * 拍品信息缓存
 *
 * @author wei.lin
 **/
@Slf4j
@Service
public class ThingSellInfoCacheService extends BaseRedisCache<ThingSellInfoCache> {

    public void set(ThingSellInfoCache cache) {
        log.info("保存拍卖缓存信息！{}", JsonUtil.bean2Json(cache));
        String beginKey = buildCacheKey(CacheConstant.AUCTION_BEGIN_CACHE, String.valueOf(cache.getId()));
        String endKey = buildCacheKey(CacheConstant.AUCTION_END_CACHE, String.valueOf(cache.getId()));
        redisTemplate.opsForValue().set(beginKey, cache, getTtl(cache.getAuctionStartTime()), TimeUnit.MILLISECONDS);
        redisTemplate.opsForValue().set(endKey, cache, getTtl(cache.getAuctionEndTime()), TimeUnit.MILLISECONDS);
    }

    public void update(ThingSellInfoCache cache) {
        log.info("更新拍卖缓存信息！{}", JsonUtil.bean2Json(cache));
        String endKey = buildCacheKey(CacheConstant.AUCTION_END_CACHE, String.valueOf(cache.getId()));
        redisTemplate.opsForValue().set(endKey, cache, getTtl(cache.getAuctionEndTime()), TimeUnit.MILLISECONDS);
    }

    public void delete(Long sellId) {
        String endKey = buildCacheKey(CacheConstant.AUCTION_END_CACHE, String.valueOf(sellId));
        redisTemplate.delete(endKey);
        log.info("删除拍卖缓存信息！sellId={}", sellId);
    }

    public ThingSellInfoCache get(String sellId) {
        String endKey = buildCacheKey(CacheConstant.AUCTION_END_CACHE, sellId);
        return redisTemplate.opsForValue().get(endKey);
    }


    protected long getTtl(Long time) {
        return time - System.currentTimeMillis();
    }


}
