package com.example.demo.service;

import com.example.demo.entity.FeatureCollection;
import com.example.demo.entity.GeoJsonGeometry;
import com.example.demo.entity.GeoJsonPolygonGeometry;

public interface GeoJsonGeometryService {

    boolean add(GeoJsonGeometry geometry);

    boolean restore();

    FeatureCollection getContainedInPolygonGeometries(GeoJsonPolygonGeometry geometry);
}
