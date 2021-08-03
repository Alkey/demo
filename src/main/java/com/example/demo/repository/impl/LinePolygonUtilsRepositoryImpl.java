package com.example.demo.repository.impl;

import com.example.demo.repository.LinePolygonUtilsRepository;
import com.example.demo.util.PostGisUtil;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import static jooq.tables.Line.LINE;
import static jooq.tables.Polygon.POLYGON;

@Repository
@RequiredArgsConstructor
public class LinePolygonUtilsRepositoryImpl implements LinePolygonUtilsRepository {
    private static final String EXCEPTION_MESSAGE = "Incorrect lineId or polygonId";
    private final DSLContext dsl;

    @Override
    public boolean isIntersects(long lineId, long polygonId) {
        return dsl.select(PostGisUtil.isIntersects(LINE.GEOMETRY, POLYGON.GEOMETRY))
                .from(LINE, POLYGON)
                .where(LINE.ID.eq(lineId), POLYGON.ID.eq(polygonId))
                .fetchOptionalInto(boolean.class)
                .orElseThrow(() -> new IllegalArgumentException(EXCEPTION_MESSAGE));
    }

    @Override
    public boolean isWithIn(long lineId, long polygonId) {
        return dsl.select(PostGisUtil.isWithIn(LINE.GEOMETRY, POLYGON.GEOMETRY))
                .from(LINE, POLYGON)
                .where(LINE.ID.eq(lineId), POLYGON.ID.eq(polygonId))
                .fetchOptionalInto(boolean.class)
                .orElseThrow(() -> new IllegalArgumentException(EXCEPTION_MESSAGE));

    }

    @Override
    public double getDistance(long lineId, long polygonId) {
        return dsl.select(PostGisUtil.stDistance(LINE.GEOMETRY, POLYGON.GEOMETRY))
                .from(LINE, POLYGON)
                .where(LINE.ID.eq(lineId), POLYGON.ID.eq(polygonId))
                .fetchOptionalInto(double.class)
                .orElseThrow(() -> new IllegalArgumentException(EXCEPTION_MESSAGE));
    }
}
