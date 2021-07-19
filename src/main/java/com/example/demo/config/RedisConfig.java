package com.example.demo.config;

import com.example.demo.entity.FeatureCollection;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class RedisConfig {
    public static final String PRODUCTS = "products";
    public static final String ALL_PRODUCTS = "allProducts";
    private static final long EXPIRATION_MINUTES = 15;

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        return new JedisConnectionFactory();
    }

    @Bean
    public RedisTemplate<String, FeatureCollection> redisTemplate() {
        RedisTemplate<String, FeatureCollection> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        return template;
    }

    @Bean
    public CacheManager cacheManager(JedisConnectionFactory factory) {
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();
        Set<String> cacheNames = Set.of(PRODUCTS, ALL_PRODUCTS);
        ConcurrentHashMap<String, RedisCacheConfiguration> configMap = new ConcurrentHashMap<>();
        configMap.put(PRODUCTS, config.entryTtl(Duration.ofMinutes(EXPIRATION_MINUTES)));
        configMap.put(ALL_PRODUCTS, config.entryTtl(Duration.ofMinutes(EXPIRATION_MINUTES)));
        return RedisCacheManager.builder(factory)
                .initialCacheNames(cacheNames)
                .withInitialCacheConfigurations(configMap)
                .build();
    }
}
