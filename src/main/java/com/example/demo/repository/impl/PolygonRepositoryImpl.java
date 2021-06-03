package com.example.demo.repository.impl;

import com.example.demo.entity.Polygon;
import com.example.demo.repository.PolygonRepository;
import com.example.demo.util.PostGisUtil;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.example.demo.jooq.sample.model.tables.Polygon.POLYGON;

@Repository
@RequiredArgsConstructor
public class PolygonRepositoryImpl implements PolygonRepository {
    private final DSLContext dsl;

    @Override
    public int add(String name, String polygon) {
        return dsl.insertInto(POLYGON)
                .set(POLYGON.NAME, name)
                .set(POLYGON.LOCATION, PostGisUtil.stPolygonFromText(polygon))
                .set(POLYGON.AREA, PostGisUtil.stArea(polygon))
                .execute();
    }

    @Override
    public Optional<Polygon> findById(long id) {
        return dsl.select(POLYGON.NAME, PostGisUtil.convertToGeoJsonAndCoordinates(POLYGON.LOCATION).as("polygon"), POLYGON.AREA)
                .from(POLYGON)
                .where(POLYGON.ID.eq(id))
                .fetchOptionalInto(Polygon.class);
    }
}
