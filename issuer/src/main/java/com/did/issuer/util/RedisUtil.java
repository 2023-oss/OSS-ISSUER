package com.did.issuer.util;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;

@RequiredArgsConstructor
@Service
public class RedisUtil {
    private final StringRedisTemplate template;

    // data 추출
    public String getData(String key){
        ValueOperations<String, String> valueOperations = template.opsForValue();
        return valueOperations.get(key);
    }

    // data 존재 확인
    public boolean existData(String key){
        return Boolean.TRUE.equals(template.hasKey(key));
    }

    // data 저장
    public void setDataExpire(String key, String value, long duration){
        ValueOperations<String, String> valueOperations = template.opsForValue();
        Duration expireDuration = Duration.ofSeconds(duration);
        valueOperations.set(key, value, expireDuration);
    }

    // data 삭제
    public void deleteData(String key){
        template.delete(key);
    }
}
