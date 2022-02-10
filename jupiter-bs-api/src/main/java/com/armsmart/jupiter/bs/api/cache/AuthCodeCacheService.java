package com.armsmart.jupiter.bs.api.cache;

import com.armsmart.common.config.JwtConfig;
import com.armsmart.jupiter.bs.api.cache.base.BaseRedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

import static com.armsmart.jupiter.bs.api.cache.constants.CacheConstant.AUTH_CODE_CACHE;

/**
 * @author wei.lin
 * @date 2021/9/24
 */
@Service
public class AuthCodeCacheService extends BaseRedisCache<String> {

    @Autowired
    private JwtConfig jwtConfig;

    public void saveAuthCode(Integer userId, String authCode) {
        String cacheKey = buildCacheKey(AUTH_CODE_CACHE, String.valueOf(userId));
        redisTemplate.opsForValue().set(cacheKey, authCode, jwtConfig.getExpiration(), TimeUnit.SECONDS);
    }

    public String getAuthCode(Integer userId) {
        String cacheKey = buildCacheKey(AUTH_CODE_CACHE, String.valueOf(userId));
        return redisTemplate.opsForValue().get(cacheKey);
    }
}
