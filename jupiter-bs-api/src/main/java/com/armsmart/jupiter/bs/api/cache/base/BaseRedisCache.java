package com.armsmart.jupiter.bs.api.cache.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Redis缓存基类
 *
 * @author wei.lin
 **/
public class BaseRedisCache<T> {

    protected RedisTemplate<String, T> redisTemplate;

    @Autowired
    protected void setRedisTemplate(RedisTemplate<String, T> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    protected String buildCacheKey(String namespace, String key) {

        return namespace + key;
    }

}
