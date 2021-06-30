package com.example.demo.repository.impl;

import org.jooq.DSLContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.example.demo.ConnectionUtils.getConnection;
import static com.example.demo.jooq.sample.model.tables.Line.LINE;
import static com.example.demo.jooq.sample.model.tables.Polygon.POLYGON;
import static com.example.demo.util.PostGisUtil.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.val;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LinePolygonUtilsRepositoryImplTest {
    private static final long INCORRECT_ID = 1;
    private static final String LINE_NAME = "line";
    private static final String LINE_GEOMETRY = "LINESTRING(28.45948219299316 49.23748866484026,28.459887206554413 49.23519618382245)";
    private static final String POLYGON_NAME = "polygon";
    private static final String POLYGON_GEOMETRY = "POLYGON((28.45634937286377 49.238784596337126,28.454246520996094 49.234665517970974,28.45986843109131 49.235338044024914,28.45634937286377 49.238784596337126))";

    private static final DSLContext dsl = getConnection();
    private final LinePolygonUtilsRepositoryImpl repository = new LinePolygonUtilsRepositoryImpl(dsl);

    @Test
    public void isIntersectsTest() {
        String lineNotIntersectsPolygon = "LINESTRING(28.460726737976074 49.23856043764552,28.461005687713623 49.23561826051159)";
        List<Long> lineIds = addLinesToDb(lineNotIntersectsPolygon);
        long polygonId = addPolygonToDb();
        assertThat(repository.isIntersects(lineIds.get(0), polygonId), is(true));
        assertThat(repository.isIntersects(lineIds.get(1), polygonId), is(false));
    }

    @Test
    public void isIntersectsThrowIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> repository.isIntersects(INCORRECT_ID, INCORRECT_ID));
    }

    @Test
    public void isWithInTest() {
        String lineWithInPolygon = "LINESTRING(28.45632791519165 49.23706135024354,28.457186222076412 49.2355341957325)";
        List<Long> lineIds = addLinesToDb(lineWithInPolygon);
        long polygonId = addPolygonToDb();
        assertThat(repository.isWithIn(lineIds.get(1), polygonId), is(true));
        assertThat(repository.isWithIn(lineIds.get(0), polygonId), is(false));
    }

    @Test
    public void isWithInTestThrowIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> repository.isWithIn(INCORRECT_ID, INCORRECT_ID));
    }

    @Test
    public void getDistanceTest() {
        String line = "LINESTRING(28.46261501312256 49.239344988615166,28.46257209777832 49.2338528701058)";
        List<Long> lineIds = addLinesToDb(line);
        long polygonId = addPolygonToDb();
        assertThat(repository.getDistance(lineIds.get(0), polygonId), is(0.0));
        assertThat(repository.getDistance(lineIds.get(1), polygonId), is(197.74091797));
    }

    @Test
    public void getDistanceThrowIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> repository.getDistance(INCORRECT_ID, INCORRECT_ID));
    }

    @AfterEach
    public void clearDb() {
        dsl.deleteFrom(LINE).execute();
        dsl.deleteFrom(POLYGON).execute();
    }

    @BeforeAll
    public static void checkDb() {
        assertThat(dsl.selectCount().from(LINE).fetchOneInto(int.class), is(0));
        assertThat(dsl.selectCount().from(POLYGON).fetchOneInto(int.class), is(0));
    }

    private long addPolygonToDb() {
        return dsl.insertInto(POLYGON, POLYGON.NAME, POLYGON.GEOMETRY, POLYGON.AREA)
                .values(field(val(POLYGON_NAME)), stGeomFromText(POLYGON_GEOMETRY), stArea(POLYGON_GEOMETRY))
                .returningResult(POLYGON.ID)
                .fetchOne()
                .into(long.class);
    }

    private List<Long> addLinesToDb(String line) {
        return dsl.insertInto(LINE, LINE.NAME, LINE.GEOMETRY, LINE.LENGTH)
                .values(field(val(LINE_NAME)), stGeomFromText(LINE_GEOMETRY), stLength(LINE_GEOMETRY))
                .values(field(val(LINE_NAME)), stGeomFromText(line), stLength(line))
                .returningResult(LINE.ID)
                .fetch()
                .into(long.class);
    }
}
