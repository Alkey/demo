package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.io.Serializable;
import java.util.List;

@Value
public class FeatureCollection implements Serializable {
    long id;
    List<GeoJsonGeometry> features;

    @JsonCreator
    public FeatureCollection(@JsonProperty("id") long id,
                             @JsonProperty("features") List<GeoJsonGeometry> features) {
        this.id = id;
        this.features = features;
    }
}
