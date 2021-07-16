package com.example.demo.service;

import com.example.demo.entity.FeatureCollection;
import com.example.demo.entity.GeoJsonPolygonGeometry;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface FeatureCollectionService {

    long add(GeoJsonPolygonGeometry geometry);

    Optional<FeatureCollection> findById(long id) throws IOException;

    List<FeatureCollection> getAll() throws IOException;

    void delete(long id);
}
