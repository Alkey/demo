package com.example.demo.service.impl;

import com.example.demo.repository.LinePolygonUtilsRepository;
import com.example.demo.service.LinePolygonUtilsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LinePolygonUtilsServiceImpl implements LinePolygonUtilsService {
    private final LinePolygonUtilsRepository repository;

    @Override
    public boolean isIntersects(long lineId, long polygonId) {
        return repository.isIntersects(lineId, polygonId);
    }

    @Override
    public boolean isWithIn(long lineId, long polygonId) {
        return repository.isWithIn(lineId, polygonId);
    }

    @Override
    public double getDistance(long lineId, long polygonId) {
        return repository.getDistance(lineId, polygonId);
    }
}
