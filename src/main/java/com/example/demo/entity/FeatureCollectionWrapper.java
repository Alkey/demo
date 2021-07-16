package com.example.demo.entity;

import lombok.Value;
import org.springframework.data.redis.core.RedisHash;

@Value
@RedisHash("Feature")
public class FeatureCollectionWrapper {
    long id;
    byte[] featureCollection;
}
