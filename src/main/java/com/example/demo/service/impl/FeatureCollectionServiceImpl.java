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
    private final FeatureCollectionRepository repository;
    private final GeoJsonGeometryService geometryService;

    @Override
    public long add(GeoJsonPolygonGeometry geometry) {
        FeatureCollection geometries = geometryService.getContainedInPolygonGeometries(geometry);
        repository.save(geometries);
        return geometries.getId();
    }

    @Override
    public Optional<FeatureCollection> findById(long id) {
        return repository.findById(id);
    }

    @Override
    public List<FeatureCollection> getAll() {
        return repository.getAll();
    }

    @Override
    public void delete(long id) {
        repository.deleteById(id);
    }
}
