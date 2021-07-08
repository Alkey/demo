package com.example.demo.repository.impl;

import com.example.demo.entity.FeatureCollection;
import com.example.demo.repository.FeatureCollectionRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.Objects;

import static com.example.demo.jooq.sample.model.tables.FeatureCollection.FEATURE_COLLECTION;

@Repository
@RequiredArgsConstructor
public class FeatureCollectionRepositoryImpl implements FeatureCollectionRepository {
    private final DSLContext dsl;

    @Override
    public long save(FeatureCollection featureCollection) {
        return Objects.requireNonNull(dsl.insertInto(FEATURE_COLLECTION, FEATURE_COLLECTION.HASH)
                .values(featureCollection.hashCode())
                .returningResult(FEATURE_COLLECTION.ID)
                .fetchOne())
                .into(long.class);
    }
}
