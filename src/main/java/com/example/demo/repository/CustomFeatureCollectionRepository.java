package com.example.demo.repository;

import com.example.demo.entity.FeatureCollection;

import java.util.List;

public interface CustomFeatureCollectionRepository {
    List<FeatureCollection> getAll();
}
