package com.reachauto.hkr.cr.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author Deng Fangzhi
 * on 2018/1/4
 */
@Component
public class ConcurrentCache {

    @Autowired
    private RedisTemplate redisTemplate;

    public Long getConcurrent(String key, long step, long timeout) {

        Long increment = redisTemplate.opsForValue().increment(key, step);
        redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
        return increment;
    }
}
