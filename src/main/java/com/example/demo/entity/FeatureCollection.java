package com.example.demo.entity;

import lombok.Value;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.List;

@Value
@RedisHash("Feature")
public class FeatureCollection implements Serializable {
    long id;
    List<GeoJsonGeometry> features;
}
