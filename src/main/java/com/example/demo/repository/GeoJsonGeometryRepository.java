package com.example.demo.repository;

import java.util.Optional;

public interface GeoJsonGeometryRepository {
    Optional<Boolean> isIntersects(long lineId, long polygonId);

    Optional<Boolean> isWithIn(long lineId, long polygonId);

    Optional<Double> getDistance(long lineId, long polygonId);

    String getPolygonIntersection(long firstPolygonId, long secondPolygonId);

    String getLineStringIntersection(long firstLineId, long secondLineId);
}
