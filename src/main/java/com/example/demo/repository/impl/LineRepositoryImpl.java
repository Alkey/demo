package com.example.demo.repository.impl;

import com.example.demo.entity.Line;
import com.example.demo.repository.LineRepository;
import com.example.demo.util.PostGisUtil;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.example.demo.jooq.sample.model.tables.Line.LINE;
import static org.jooq.impl.DSL.field;

@Repository
@RequiredArgsConstructor
public class LineRepositoryImpl implements LineRepository {
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
}
