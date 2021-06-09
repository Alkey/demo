package com.example.demo.repository.impl;

import com.example.demo.jooq.sample.model.tables.Line;
import com.example.demo.jooq.sample.model.tables.Polygon;
import com.example.demo.repository.GeoJsonGeometryRepository;
import com.example.demo.util.PostGisUtil;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.example.demo.jooq.sample.model.tables.Line.LINE;
import static com.example.demo.jooq.sample.model.tables.Polygon.POLYGON;

@Repository
@RequiredArgsConstructor
public class GeoJsonGeometryRepositoryImpl implements GeoJsonGeometryRepository {
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

    @Override
    public String getPolygonIntersection(long firstPolygonId, long secondPolygonId) {
        Polygon firstPolygon = POLYGON.as("firstPolygon");
        Polygon secondPolygon = POLYGON.as("secondPolygon");
        return dsl.select(PostGisUtil.convertToGeoJson(PostGisUtil.stIntersection(firstPolygon.GEOMETRY, secondPolygon.GEOMETRY)))
                .from(firstPolygon, secondPolygon)
                .where(firstPolygon.ID.eq(firstPolygonId), secondPolygon.ID.eq(secondPolygonId))
                .fetchOneInto(String.class);
    }

    @Override
    public String getLineStringIntersection(long firstLineId, long secondLineId) {
        Line firstLine = LINE.as("firstLine");
        Line secondLine = LINE.as("secondLine");
        return dsl.select(PostGisUtil.convertToGeoJsonAndCoordinates(PostGisUtil.stIntersection(firstLine.GEOMETRY, secondLine.GEOMETRY)))
                .from(firstLine, secondLine)
                .where(firstLine.ID.eq(firstLineId), secondLine.ID.eq(secondLineId))
                .fetchOneInto(String.class);
    }
}
