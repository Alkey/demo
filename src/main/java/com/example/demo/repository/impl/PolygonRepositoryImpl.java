package com.example.demo.repository.impl;

import com.example.demo.converter.GeoJsonGeometryConverter;
import com.example.demo.entity.GeoJsonGeometry;
import com.example.demo.entity.Polygon;
import com.example.demo.repository.PolygonRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.example.demo.util.PostGisUtil.*;
import static jooq.tables.Polygon.POLYGON;
import static org.jooq.impl.DSL.*;

@Repository
@RequiredArgsConstructor
public class PolygonRepositoryImpl implements PolygonRepository {
    private static final String FIRST_POLYGON = "first_polygon";
    private static final String FIELD_NAME = "geometry";
    private static final String FIRST_POLYGON_GEOMETRY = "first_polygon_geometry";
    private final GeoJsonGeometryConverter converter;
    private final DSLContext dsl;

    @Override
    public int add(String name, String polygon) {
        return dsl.insertInto(POLYGON)
                .set(POLYGON.NAME, name)
                .set(POLYGON.GEOMETRY, stGeomFromText(polygon))
                .set(POLYGON.AREA, stArea(polygon))
                .execute();
    }

    @Override
    public Optional<Polygon> findById(long id) {
        return dsl.select(POLYGON.ID, POLYGON.NAME, convertToGeoJsonAndCoordinates(POLYGON.GEOMETRY).as(FIELD_NAME), POLYGON.AREA)
                .from(POLYGON)
                .where(POLYGON.ID.eq(id))
                .fetchOptionalInto(Polygon.class);
    }

    @Override
    public String getPolygonIntersection(long firstPolygonId, long secondPolygonId) {
        return dsl.with(name(FIRST_POLYGON))
                .as(select(POLYGON.GEOMETRY
                        .as(FIRST_POLYGON_GEOMETRY))
                        .from(POLYGON)
                        .where(POLYGON.ID.eq(firstPolygonId)))
                .select(convertToGeoJson(
                        stIntersection(field(name(FIRST_POLYGON_GEOMETRY), String.class), POLYGON.GEOMETRY)))
                .from(table(name(FIRST_POLYGON)), POLYGON)
                .where(POLYGON.ID.eq(secondPolygonId))
                .fetchOneInto(String.class);
    }

    @Override
    public List<GeoJsonGeometry> getContainedInPolygonGeometries(String polygon) {
        return dsl.select(convertToGeoJson(POLYGON.GEOMETRY).as(FIELD_NAME))
                .from(POLYGON)
                .where(isIntersects(POLYGON.GEOMETRY, stGeomFromText(polygon)))
                .fetch(name(FIELD_NAME), converter);
    }
}
