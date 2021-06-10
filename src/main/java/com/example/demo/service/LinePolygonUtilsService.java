package com.example.demo.service;

public interface LinePolygonUtilsService {
    boolean isIntersects(long lineId, long polygonId);

    boolean isWithIn(long lineId, long polygonId);

    double getDistance(long lineId, long polygonId);
}
