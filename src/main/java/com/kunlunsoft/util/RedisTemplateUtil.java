package com.kunlunsoft.util;

import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;

public class RedisTemplateUtil {
    public static List<String> getValueList(StringRedisTemplate stringRedisTemplate, String key) {
        return stringRedisTemplate.opsForList().range(key, 0, -1);
    }
}
