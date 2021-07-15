package com.example.demo.repository.impl;

import com.example.demo.entity.FeatureCollection;
import com.example.demo.repository.CustomFeatureCollectionRepository;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class CustomFeatureCollectionRepositoryImpl implements CustomFeatureCollectionRepository {
    private final RedisTemplate<String, FeatureCollection> redisTemplate;
    private final HashOperations<String, String, FeatureCollection> hashOperations;

    public CustomFeatureCollectionRepositoryImpl(RedisTemplate<String, FeatureCollection> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.hashOperations = redisTemplate.opsForHash();
    }

    @Override
    public List<FeatureCollection> getAll() {
        Set<String> keys = redisTemplate.keys("*");
        if (keys != null) {
            return keys.stream()
                    .skip(1)
                    .map(key -> hashOperations.values(key).get(0))
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
}
