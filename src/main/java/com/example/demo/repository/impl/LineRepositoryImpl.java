package com.example.demo.repository.impl;

import com.example.demo.entity.Line;
import com.example.demo.repository.LineRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.example.demo.jooq.sample.model.tables.Line.LINE;
import static com.example.demo.util.PostGisUtil.*;
import static org.jooq.impl.DSL.*;

@Repository
@RequiredArgsConstructor
public class LineRepositoryImpl implements LineRepository {
    private static final String FIRST_LINE = "first_line";
    private static final String FIRST_LINE_GEOMETRY = "first_line_geometry";
    private final DSLContext dsl;

    @Override
    public int add(String name, String location) {
        return dsl.insertInto(LINE)
                .set(LINE.NAME, name)
                .set(field("geometry", String.class), stGeomFromText(location))
                .set(field("length", double.class), stLength(location))
                .execute();
    }

    @Override
    public Optional<Line> findById(long id) {
        return dsl.select(LINE.ID, LINE.NAME, convertToGeoJsonAndCoordinates(LINE.GEOMETRY).as("geometry"), LINE.LENGTH)
                .from(LINE)
                .where(LINE.ID.eq(id))
                .fetchOptionalInto(Line.class);
    }

    @Override
    public String getLineStringIntersection(long firstLineId, long secondLineId) {
        return dsl.with(name(FIRST_LINE))
                .as(select(LINE.GEOMETRY
                        .as(FIRST_LINE_GEOMETRY))
                        .from(LINE)
                        .where(LINE.ID.eq(firstLineId)))
                .select(convertToGeoJsonAndCoordinates(
                        stIntersection(field(name(FIRST_LINE_GEOMETRY), String.class), LINE.GEOMETRY)))
                .from(table(name(FIRST_LINE)), LINE)
                .where(LINE.ID.eq(secondLineId))
                .fetchOneInto(String.class);
    }

    @Override
    public List<String> getContainedInPolygonGeometries(String polygon) {
        return dsl.select(convertToGeoJsonAndCoordinates(LINE.GEOMETRY))
                .from(LINE)
                .where(stIntersects(LINE.GEOMETRY, stGeomFromText(polygon)))
                .or((stWithIn(LINE.GEOMETRY, stGeomFromText(polygon))))
                .fetch()
                .into(String.class);
    }
}
