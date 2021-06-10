package com.example.demo.repository.impl;

import com.example.demo.repository.SpatialRelationshipsRepository;
import com.example.demo.util.PostGisUtil;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.example.demo.jooq.sample.model.tables.Line.LINE;
import static com.example.demo.jooq.sample.model.tables.Polygon.POLYGON;

@Repository
@RequiredArgsConstructor
public class SpatialRelationshipsRepositoryImpl implements SpatialRelationshipsRepository {
    private final DSLContext dsl;

    @Override
    public Optional<Boolean> isIntersects(long lineId, long polygonId) {
        return dsl.select(PostGisUtil.stIntersects(LINE.GEOMETRY, POLYGON.GEOMETRY))
                .from(LINE, POLYGON)
                .where(LINE.ID.eq(lineId), POLYGON.ID.eq(polygonId))
                .fetchOptionalInto(boolean.class);
    }

    @Override
    public Optional<Boolean> isWithIn(long lineId, long polygonId) {
        return dsl.select(PostGisUtil.stWithIn(LINE.GEOMETRY, POLYGON.GEOMETRY))
                .from(LINE, POLYGON)
                .where(LINE.ID.eq(lineId), POLYGON.ID.eq(polygonId))
                .fetchOptionalInto(boolean.class);
    }

    @Override
    public Optional<Double> getDistance(long lineId, long polygonId) {
        return dsl.select(PostGisUtil.stDistance(LINE.GEOMETRY, POLYGON.GEOMETRY))
                .from(LINE, POLYGON)
                .where(LINE.ID.eq(lineId), POLYGON.ID.eq(polygonId))
                .fetchOptionalInto(double.class);
    }
}
