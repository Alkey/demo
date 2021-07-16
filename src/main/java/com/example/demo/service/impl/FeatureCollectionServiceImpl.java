package com.example.demo.service.impl;

import com.example.demo.converter.FeatureCollectionConverter;
import com.example.demo.entity.FeatureCollection;
import com.example.demo.entity.FeatureCollectionWrapper;
import com.example.demo.entity.GeoJsonPolygonGeometry;
import com.example.demo.repository.FeatureCollectionWrapperRepository;
import com.example.demo.service.FeatureCollectionService;
import com.example.demo.service.GeoJsonGeometryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FeatureCollectionServiceImpl implements FeatureCollectionService {
    private final FeatureCollectionWrapperRepository repository;
    private final GeoJsonGeometryService geometryService;
    private final FeatureCollectionConverter converter;

    @Override
    public long add(GeoJsonPolygonGeometry geometry) throws JsonProcessingException {
        FeatureCollection featureCollection = geometryService.getContainedInPolygonGeometries(geometry);
        FeatureCollectionWrapper wrapper = converter.toWrapper(featureCollection);
        repository.save(wrapper);
        return repository.save(wrapper).getId();
    }

    @Override
    public Optional<FeatureCollection> findById(long id) throws IOException {
        Optional<FeatureCollectionWrapper> optional = repository.findById(id);
        if (optional.isPresent()) {
            FeatureCollectionWrapper wrapper = optional.get();
            return Optional.of(converter.fromWrapper(wrapper));
        }
        return Optional.empty();
    }

    @Override
    public List<FeatureCollection> getAll() throws IOException {
        Iterable<FeatureCollectionWrapper> wrappers = repository.findAll();
        List<FeatureCollection> collections = new ArrayList<>();
        for (FeatureCollectionWrapper wrapper : wrappers) {
            collections.add(converter.fromWrapper(wrapper));
        }
        return collections;
    }

    @Override
    public void delete(long id) {
        repository.deleteById(id);
    }
}
