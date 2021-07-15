package com.example.demo.repository;

import com.example.demo.entity.FeatureCollection;
import org.springframework.data.repository.CrudRepository;

public interface FeatureCollectionRepository extends CrudRepository<FeatureCollection, Long>, CustomFeatureCollectionRepository {
}
