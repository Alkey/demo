package com.example.demo.repository.impl;

import com.example.demo.entity.FeatureCollection;
import com.example.demo.repository.FeatureCollectionRepository;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FeatureCollectionRepositoryImpl implements FeatureCollectionRepository {
    private static final String KEY = "Feature";
    private final HashOperations<String, Long, FeatureCollection> hashOperations;

    public FeatureCollectionRepositoryImpl(RedisTemplate<String, FeatureCollection> restTemplate) {
        this.hashOperations = restTemplate.opsForHash();
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
