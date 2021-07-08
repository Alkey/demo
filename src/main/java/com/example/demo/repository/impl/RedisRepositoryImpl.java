package com.example.demo.repository.impl;

import com.example.demo.entity.FeatureCollection;
import com.example.demo.repository.RedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class RedisRepositoryImpl implements RedisRepository {
    private static final String KEY = "Feature";
    private final RedisTemplate<String, FeatureCollection> restTemplate;
    private HashOperations<String, Long, FeatureCollection> hashOperations;

    @PostConstruct
    public void init() {
        hashOperations = restTemplate.opsForHash();
    }

    @Override
    public boolean add(FeatureCollection featureCollection) {
        return hashOperations.putIfAbsent(KEY, featureCollection.getId(), featureCollection);
    }

    @Override
    public FeatureCollection findById(long id) {
        return hashOperations.get(KEY, id);
    }

    @Override
    public List<FeatureCollection> getAll() {
        return hashOperations.values(KEY);
    }

    @Override
    public void delete(long id) {
        hashOperations.delete(KEY, id);
    }
}
