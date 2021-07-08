package com.example.demo.service;

import com.example.demo.entity.FeatureCollection;
import com.example.demo.entity.GeoJsonGeometry;

public interface GeoJsonGeometryService {

    boolean add(GeoJsonGeometry geometry);

    boolean restore();

    FeatureCollection getContainedInPolygonGeometries(GeoJsonGeometry geometry);
}
