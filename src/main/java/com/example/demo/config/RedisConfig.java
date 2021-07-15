package com.example.demo.config;

import com.example.demo.converter.BytesToFeatureCollectionConverter;
import com.example.demo.converter.FeatureCollectionToBytesConverter;
import com.example.demo.entity.FeatureCollection;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.convert.RedisCustomConversions;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class RedisConfig {

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        return new JedisConnectionFactory();
    }

    @Bean
    public RedisTemplate<String, FeatureCollection> redisTemplate() {
        RedisTemplate<String, FeatureCollection> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        template.setHashKeySerializer(RedisSerializer.string());
        template.setKeySerializer(RedisSerializer.string());
        return template;
    }

    @Bean
    public RedisCustomConversions redisCustomConversions(FeatureCollectionToBytesConverter toBytesConverter,
                                                         BytesToFeatureCollectionConverter bytesToFeatureCollectionConverter) {
        return new RedisCustomConversions(List.of(bytesToFeatureCollectionConverter, toBytesConverter));
    }
}
