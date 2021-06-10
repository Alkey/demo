package com.example.demo.service;

import java.util.Optional;

public interface SpatialRelationshipsService {
    Optional<Boolean> isIntersects(long lineId, long polygonId);

    Optional<Boolean> isWithIn(long lineId, long polygonId);

    Optional<Double> getDistance(long lineId, long polygonId);
}
