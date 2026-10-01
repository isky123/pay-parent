package com.pay.infrastructure.cache;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class RedisLockService {
    private final RedisTemplate<String, String> redisTemplate;

    private static final String LOCK_PREFIX = "pay:lock:";
    private static final long DEFAULT_EXPIRE = 30;

    public boolean lock(String key, String value) {
        String lockKey = LOCK_PREFIX + key;
        Boolean result = redisTemplate.opsForValue().setIfAbsent(lockKey, value, DEFAULT_EXPIRE, TimeUnit.SECONDS);
        return result != null && result;
    }

    public void unlock(String key) {
        String lockKey = LOCK_PREFIX + key;
        redisTemplate.delete(lockKey);
    }

    public void set(String key, String value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }
}
