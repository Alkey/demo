package com.example.demo.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class FeatureCollection implements Serializable {
    private long id;
    private List<GeoJsonGeometry> features;
}
