package com.spring.api.common;

import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
public class RedisService {
    private final RedisTemplate<String, Object> redisTemplate;

    public RedisService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public boolean check() {
        try (RedisConnection connection = Objects.requireNonNull(redisTemplate.getConnectionFactory()).getConnection()) {
            String pingResponse = connection.ping();
            return "PONG".equalsIgnoreCase(pingResponse);
        } catch (Exception e) {
            return false;
        }
    }

    public void setRedis(String key, String value, int ttl) {
        ValueOperations<String, Object> data = redisTemplate.opsForValue();
        data.set(key, value, ttl, TimeUnit.SECONDS);
    }

    public String getRedis(String key) {
        ValueOperations<String, Object> data = redisTemplate.opsForValue();
        Object value = data.get(key);
        if (value != null) {
            return value.toString();
        }
        return null;
    }

    public String deleteRedis(String key) {
        ValueOperations<String, Object> data = redisTemplate.opsForValue();
        data.getAndDelete(key);
        return "deleted";
    }

    public Map<Object, Object> getSessionData(String sessionId) {
        String sessionKey = "spring:session:sessions:" + sessionId;
        HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
        return hashOperations.entries(sessionKey);
    }

    public boolean deleteSession(String sessionId) {
        String sessionKey = "spring:session:sessions:" + sessionId;
        return redisTemplate.delete(sessionKey);
    }
}
