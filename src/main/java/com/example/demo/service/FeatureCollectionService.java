package com.example.demo.service;

import com.example.demo.entity.FeatureCollection;
import com.example.demo.entity.GeoJsonGeometry;

import java.util.List;
import java.util.Optional;

public interface FeatureCollectionService {

    boolean add(GeoJsonGeometry geometry);

    Optional<FeatureCollection> findById(long id);

    List<FeatureCollection> getAll();

    void delete(long id);
}
