package com.example.demo.service.impl;

import com.example.demo.repository.SpatialRelationshipsRepository;
import com.example.demo.service.SpatialRelationshipsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SpatialRelationshipsImpl implements SpatialRelationshipsService {
    private final SpatialRelationshipsRepository repository;

    @Override
    public Optional<Boolean> isIntersects(long lineId, long polygonId) {
        return repository.isIntersects(lineId, polygonId);
    }

    @Override
    public Optional<Boolean> isWithIn(long lineId, long polygonId) {
        return repository.isWithIn(lineId, polygonId);
    }

    @Override
    public Optional<Double> getDistance(long lineId, long polygonId) {
        return repository.getDistance(lineId, polygonId);
    }
}
