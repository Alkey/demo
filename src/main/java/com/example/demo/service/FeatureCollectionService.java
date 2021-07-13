package com.example.demo.service;

import com.example.demo.entity.FeatureCollection;
import com.example.demo.entity.GeoJsonPolygonGeometry;

import java.util.List;
import java.util.Optional;

public interface FeatureCollectionService {

    long add(GeoJsonPolygonGeometry geometry);

    Optional<FeatureCollection> findById(long id);

    List<FeatureCollection> getAll();

    void delete(long id);
}
