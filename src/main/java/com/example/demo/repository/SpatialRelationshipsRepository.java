package com.example.demo.repository;

import java.util.Optional;

public interface SpatialRelationshipsRepository {
    Optional<Boolean> isIntersects(long lineId, long polygonId);

    Optional<Boolean> isWithIn(long lineId, long polygonId);

    Optional<Double> getDistance(long lineId, long polygonId);
}
