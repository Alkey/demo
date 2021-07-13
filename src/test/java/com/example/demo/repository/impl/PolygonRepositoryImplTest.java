package com.example.demo.repository.impl;

import com.example.demo.converter.GeoJsonGeometryConverter;
import com.example.demo.entity.Polygon;
import org.jooq.DSLContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.example.demo.ConnectionUtils.getConnection;
import static com.example.demo.jooq.sample.model.tables.Polygon.POLYGON;
import static com.example.demo.util.PostGisUtil.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.val;
import static org.mockito.Mockito.spy;

public class PolygonRepositoryImplTest {
    private static final String NAME_FIRST = "polygon";
    private static final String NAME_SECOND = "polygon_second";
    private static final String POLYGON_FIRST = "POLYGON((28.45634937286377 49.238784596337126,28.454246520996094 49.234665517970974,28.45986843109131 49.235338044024914,28.45634937286377 49.238784596337126))";
    private static final String POLYGON_SECOND = "POLYGON((28.45637083053589 49.236570984675524,28.455727100372314 49.23456744048976,28.46012592315674 49.23483365034289,28.45637083053589 49.236570984675524))";
    private static final DSLContext dsl = getConnection();
    private final GeoJsonGeometryConverter converter = spy(GeoJsonGeometryConverter.class);
    private final PolygonRepositoryImpl repository = new PolygonRepositoryImpl(converter, dsl);

    @Test
    public void addPolygonTest() {
        int addedRows = 1;
        double area = 88051.12520456314;
        assertThat(repository.add(NAME_FIRST, POLYGON_FIRST), is(addedRows));
        Polygon polygon = dsl.select(POLYGON.ID, POLYGON.NAME, stAsText(POLYGON.GEOMETRY).as("geometry"), POLYGON.AREA)
                .from(POLYGON)
                .where(POLYGON.NAME.eq(NAME_FIRST))
                .fetchOneInto(Polygon.class);
        assertThat(polygon.getName(), is(NAME_FIRST));
        assertThat(polygon.getGeometry(), is(POLYGON_FIRST));
        assertThat(polygon.getArea(), is(area));
    }

    @Test
    public void findByIdTest() {
        Polygon polygon = dsl.insertInto(POLYGON, POLYGON.NAME, POLYGON.GEOMETRY, POLYGON.AREA)
                .values(field(val(NAME_FIRST)), stGeomFromText(POLYGON_FIRST), stArea(POLYGON_FIRST))
                .returningResult(POLYGON.ID, POLYGON.NAME, convertToGeoJsonAndCoordinates(POLYGON.GEOMETRY), POLYGON.AREA)
                .fetchOne()
                .into(Polygon.class);
        assertThat(repository.findById(polygon.getId()).get(), is(polygon));
    }

    @Test
    public void getPolygonIntersectionTest() {
        String polygon = "{\"type\":\"Polygon\",\"coordinates\":[[[28.459206795,49.235258895],[28.455819053,49.234853634],[28.456370831,49.236570985],[28.459206795,49.235258895]]]}";
        List<Long> polygonIds = dsl.insertInto(POLYGON, POLYGON.NAME, POLYGON.GEOMETRY, POLYGON.AREA)
                .values(field(val(NAME_FIRST)), stGeomFromText(POLYGON_FIRST), stArea(POLYGON_FIRST))
                .values(field(val(NAME_SECOND)), stGeomFromText(POLYGON_SECOND), stArea(POLYGON_SECOND))
                .returningResult(POLYGON.ID)
                .fetch()
                .into(long.class);
        assertThat(repository.getPolygonIntersection(polygonIds.get(0), polygonIds.get(1)), is(polygon));
    }

    @AfterEach
    public void clearDb() {
        dsl.deleteFrom(POLYGON).execute();
    }

    @BeforeAll
    public static void checkDb() {
        assertThat(dsl.selectCount().from(POLYGON).fetchOneInto(int.class), is(0));
    }
}
