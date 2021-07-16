package com.example.demo.repository;

import com.example.demo.entity.GeoJsonGeometry;
import com.example.demo.entity.Polygon;

import java.util.List;
import java.util.Optional;

public interface PolygonRepository {

    int add(String name, String polygon);

    Optional<Polygon> findById(long id);

    String getPolygonIntersection(long firstPolygonId, long secondPolygonId);

    List<GeoJsonGeometry> getContainedInPolygonGeometries(String polygon);
}
