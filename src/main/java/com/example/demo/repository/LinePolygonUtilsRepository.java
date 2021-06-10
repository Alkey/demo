package com.example.demo.repository;

public interface LinePolygonUtilsRepository {
    boolean isIntersects(long lineId, long polygonId);

    boolean isWithIn(long lineId, long polygonId);

    double getDistance(long lineId, long polygonId);
}
