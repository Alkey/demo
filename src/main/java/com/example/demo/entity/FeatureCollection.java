package com.example.demo.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class FeatureCollection implements Serializable {
    long id;
    List<GeoJsonGeometry> features;
}
