package com.example.demo.repository.impl;

import com.example.demo.entity.Line;
import org.jooq.DSLContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.example.demo.ConnectionUtils.getConnection;
import static com.example.demo.jooq.sample.model.tables.Line.LINE;
import static com.example.demo.util.PostGisUtil.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.val;

public class LineRepositoryImplTest {
    private static final String NAME = "name";
    private static final String NAME_SECOND = "second_name";
    private static final String LOCATION = "LINESTRING(28.49578857421875 49.26780455063753,28.500595092773438 49.24001745095546)";
    private static final String LOCATION_SECOND = "LINESTRING(28.47484588623047 49.2513358593714,28.515357971191406 49.24887068449445)";
    private static final DSLContext dsl = getConnection();
    private final LineRepositoryImpl repository = new LineRepositoryImpl(dsl);

    @Test
    public void addLineTest() {
        double length = 3110.0793182072207;
        int addedRows = 1;
        assertThat(repository.add(NAME, LOCATION), is(addedRows));
        Line line = dsl.select(LINE.ID, LINE.NAME, stAsText(LINE.GEOMETRY).as("geometry"), LINE.LENGTH)
                .from(LINE)
                .where(LINE.NAME.eq(NAME))
                .fetchOneInto(Line.class);
        assertThat(line.getName(), is(NAME));
        assertThat(line.getGeometry(), is(LOCATION));
        assertThat(line.getLength(), is(length));
    }

    @Test
    public void findByIdTest() {
        Line line = dsl.insertInto(LINE)
                .set(LINE.NAME, NAME)
                .set(field("geometry", String.class), stGeomFromText(LOCATION))
                .set(field("length", double.class), stLength(LOCATION))
                .returningResult(LINE.ID, LINE.NAME, convertToGeoJsonAndCoordinates(LINE.GEOMETRY), LINE.LENGTH)
                .fetchOne()
                .into(Line.class);
        assertThat(repository.findById(line.getId()).get(), is(line));
    }

    @Test
    public void getLineStringIntersectionTest() {
        String point = "[28.498890357,49.249872745]";
        List<Long> lineIds = dsl.insertInto(LINE, LINE.NAME, LINE.GEOMETRY, LINE.LENGTH)
                .values(field(val(NAME)), stGeomFromText(LOCATION), stLength(LOCATION))
                .values(field(val(NAME_SECOND)), stGeomFromText(LOCATION_SECOND), stLength(LOCATION_SECOND))
                .returningResult(LINE.ID)
                .fetch()
                .into(long.class);
        assertThat(repository.getLineStringIntersection(lineIds.get(0), lineIds.get(1)), is(point));
    }

    @AfterEach
    public void clearDb() {
        dsl.deleteFrom(LINE).execute();
    }

    @BeforeAll
    public static void checkDb() {
        assertThat(dsl.selectCount().from(LINE).fetchOneInto(int.class), is(0));
    }
}
