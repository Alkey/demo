package com.example.demo.service;

import com.example.demo.entity.GeoJsonGeometry;
import com.example.demo.entity.Point;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Optional;

public interface GeoJsonGeometryService {
    boolean add(GeoJsonGeometry geometry);

    Optional<Boolean> isIntersects(long lineId, long polygonId);

    Optional<Boolean> isWithIn(long lineId, long polygonId);

    Optional<Double> getDistance(long lineId, long polygonId);

    GeoJsonGeometry getPolygonIntersection(long firstPolygonId, long secondPolygonId) throws JsonProcessingException;

    Optional<Point> getLineStringIntersection(long firstLineId, long secondLineId) throws JsonProcessingException;
}
