package com.example.demo.repository;

import com.example.demo.entity.FeatureCollection;

import java.util.List;

public interface FeatureCollectionRepository {

    boolean add(FeatureCollection featureCollection);

    FeatureCollection findById(long id);

    List<FeatureCollection> getAll();

    void delete(long id);
}
