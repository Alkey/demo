package com.example.demo.repository.impl;

import com.example.demo.entity.Polygon;
import com.example.demo.repository.PolygonRepository;
import com.example.demo.util.PostGisUtil;
import lombok.RequiredArgsConstructor;
import org.jooq.CommonTableExpression;
import org.jooq.DSLContext;
import org.jooq.Record2;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.example.demo.jooq.sample.model.tables.Polygon.POLYGON;
import static org.jooq.impl.DSL.*;

@Repository
@RequiredArgsConstructor
public class PolygonRepositoryImpl implements PolygonRepository {
    private static final String FIRST_POLYGON = "first_polygon";
    private static final String FIRST_POLYGON_ID = "first_polygon_id";
    private static final String FIRST_POLYGON_GEOMETRY = "first_polygon_geometry";
    private final DSLContext dsl;

    @Override
    public int add(String name, String polygon) {
        return dsl.insertInto(POLYGON)
                .set(POLYGON.NAME, name)
                .set(POLYGON.GEOMETRY, PostGisUtil.stGeomFromText(polygon))
                .set(POLYGON.AREA, PostGisUtil.stArea(polygon))
                .execute();
    }

    @Override
    public Optional<Polygon> findById(long id) {
        return dsl.select(POLYGON.ID, POLYGON.NAME, PostGisUtil.convertToGeoJsonAndCoordinates(POLYGON.GEOMETRY).as("geometry"), POLYGON.AREA)
                .from(POLYGON)
                .where(POLYGON.ID.eq(id))
                .fetchOptionalInto(Polygon.class);
    }

    @Override
    public String getPolygonIntersection(long firstPolygonId, long secondPolygonId) {
        CommonTableExpression<Record2<Long, String>> firstPolygon = name("firstPolygon")
                .fields(POLYGON.ID.getName(), POLYGON.GEOMETRY.getName())
                .as(select(POLYGON.ID, POLYGON.GEOMETRY).from(POLYGON));
        return dsl.with(name(FIRST_POLYGON))
                .as(select(POLYGON.ID.as(FIRST_POLYGON_ID), POLYGON.GEOMETRY.as(FIRST_POLYGON_GEOMETRY)).from(POLYGON))
                .select(PostGisUtil.convertToGeoJson(PostGisUtil.stIntersection(field(name(FIRST_POLYGON_GEOMETRY), String.class), POLYGON.GEOMETRY)))
                .from(table(name(FIRST_POLYGON)), POLYGON)
                .where(field(name(FIRST_POLYGON_ID)).eq(firstPolygonId), POLYGON.ID.eq(secondPolygonId))
                .fetchOneInto(String.class);
    }
}
