package com.example.demo.repository.impl;

import com.example.demo.entity.Line;
import com.example.demo.repository.LineRepository;
import com.example.demo.util.PostGisUtil;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.example.demo.jooq.sample.model.tables.Line.LINE;
import static org.jooq.impl.DSL.*;

@Repository
@RequiredArgsConstructor
public class LineRepositoryImpl implements LineRepository {
    private static final String FIRST_LINE = "first_line";
    private static final String FIRST_LINE_ID = "first_line_id";
    private static final String FIRST_LINE_GEOMETRY = "first_line_geometry";
    private final DSLContext dsl;

    @Override
    public int add(String name, String location) {
        return dsl.insertInto(LINE)
                .set(LINE.NAME, name)
                .set(field("geometry", String.class), PostGisUtil.stGeomFromText(location))
                .set(field("length", double.class), PostGisUtil.stLength(location))
                .execute();
    }

    @Override
    public Optional<Line> findById(long id) {
        return dsl.select(LINE.ID, LINE.NAME, PostGisUtil.convertToGeoJsonAndCoordinates(LINE.GEOMETRY).as("geometry"), LINE.LENGTH)
                .from(LINE)
                .where(LINE.ID.eq(id))
                .fetchOptionalInto(Line.class);
    }

    @Override
    public String getLineStringIntersection(long firstLineId, long secondLineId) {
        return  dsl.with(name(FIRST_LINE))
                .as(select(LINE.ID.as(FIRST_LINE_ID), LINE.GEOMETRY.as(FIRST_LINE_GEOMETRY)).from(LINE))
                .select(PostGisUtil.convertToGeoJsonAndCoordinates(PostGisUtil.stIntersection(field(name(FIRST_LINE_GEOMETRY), String.class), LINE.GEOMETRY)))
                .from(table(name(FIRST_LINE)), LINE)
                .where(field(name(FIRST_LINE_ID)).eq(firstLineId), LINE.ID.eq(secondLineId))
                .fetchOneInto(String.class);
    }
}
