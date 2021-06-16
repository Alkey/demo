package com.example.demo.service.impl;

import com.example.demo.repository.LinePolygonUtilsRepository;
import com.example.demo.service.LinePolygonUtilsService;
import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LinePolygonUtilsServiceImpl implements LinePolygonUtilsService {
    private static final String INTERSECTS_TIMER = "intersects_time";
    private static final String WITHIN_TIMER = "within_time";
    private static final String DISTANCE_TIMER = "distance_time";
    private final LinePolygonUtilsRepository repository;

    @Timed(value = INTERSECTS_TIMER)
    @Override
    public boolean isIntersects(long lineId, long polygonId) {
        return repository.isIntersects(lineId, polygonId);
    }

    @Timed(value = WITHIN_TIMER)
    @Override
    public boolean isWithIn(long lineId, long polygonId) {
        return repository.isWithIn(lineId, polygonId);
    }

    @Timed(value = DISTANCE_TIMER)
    @Override
    public double getDistance(long lineId, long polygonId) {
        return repository.getDistance(lineId, polygonId);
    }
}
