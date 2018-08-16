package com.kunlunsoft.config.redis;

import com.kunlunsoft.entity.User;
import com.kunlunsoft.service.redis.RedisLongSerializer;
import com.kunlunsoft.service.redis.RedisObjectSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.StringUtils;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;

@Configuration
public class RedisSentinelConfig {

    @Value("${spring.redis.sentinel.nodes}")
    private String redisHosts;
    @Value("${spring.redis.sentinel.master}")
    private String master;
    @Value("${spring.redis.pool.max-idle:300}")
    private int maxIdle;
    @Value("${redis.maxTotal:600}")
    private int maxTotal;
    @Value("${redis.maxWaitMillis:3000}")
    private int maxWaitMillis;
    @Value("${redis.testOnBorrow:true}")
    private boolean testOnBorrow;
    @Value("${spring.redis.sentinel.password:}")
    private String pass;

    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMaxTotal(maxTotal);
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
        return jedisPoolConfig;

    }

    public RedisSentinelConfiguration jedisSentinelConfig() {
        String[] hosts = redisHosts.trim().split(",");
        HashSet<String> sentinelHostAndPorts = new HashSet<>();
        for (String hn : hosts) {
            sentinelHostAndPorts.add(hn.trim());
        }
        return new RedisSentinelConfiguration(master, sentinelHostAndPorts);

    }

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {

        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(jedisSentinelConfig(),
                jedisPoolConfig());
        if (!StringUtils.isEmpty(pass))
            jedisConnectionFactory.setPassword(pass.trim());
        return jedisConnectionFactory;
    }

    @Bean
    public RedisTemplate<String, User> redisTemplate(JedisConnectionFactory jedisConnectionFactory) {

        RedisTemplate<String, User> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new RedisObjectSerializer());
        return redisTemplate;
    }

    /***
     * 设置 Long 的编码解码器
     * @param jedisConnectionFactory
     * @return
     */
    @Bean(name = "longRedisTemplate")
    public RedisTemplate<String, Long> longRedisTemplate(JedisConnectionFactory jedisConnectionFactory) {

        RedisTemplate<String, Long> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new RedisLongSerializer());
        return redisTemplate;
    }

    @Bean
    public StringRedisTemplate getStringRedisTemplate(JedisConnectionFactory jedisConnectionFactory) {
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate(jedisConnectionFactory);
        return stringRedisTemplate;
    }
}
