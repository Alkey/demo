package com.example.demo.service.impl;

import com.example.demo.entity.FeatureCollection;
import com.example.demo.entity.GeoJsonPolygonGeometry;
import com.example.demo.repository.FeatureCollectionRepository;
import com.example.demo.service.FeatureCollectionService;
import com.example.demo.service.GeoJsonGeometryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FeatureCollectionServiceImpl implements FeatureCollectionService {
    private final FeatureCollectionRepository redisRepository;
    private final GeoJsonGeometryService geometryService;

    @Override
    public long add(GeoJsonPolygonGeometry geometry) {
        FeatureCollection geometries = geometryService.getContainedInPolygonGeometries(geometry);
        return redisRepository.add(geometries) ? geometries.getId() : 0;
    }

    @Override

    public Optional<FeatureCollection> findById(long id) {
        return Optional.of(redisRepository.findById(id));
    }

    @Override
    public List<FeatureCollection> getAll() {
        return redisRepository.getAll();
    }

    @Override
    public void delete(long id) {
        redisRepository.delete(id);
    }
}
