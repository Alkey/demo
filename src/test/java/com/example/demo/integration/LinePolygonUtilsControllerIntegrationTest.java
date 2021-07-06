package com.example.demo.integration;

import org.jooq.DSLContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static com.example.demo.ConnectionUtils.getConnection;
import static com.example.demo.jooq.sample.model.tables.Line.LINE;
import static com.example.demo.jooq.sample.model.tables.Polygon.POLYGON;
import static com.example.demo.util.PostGisUtil.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.val;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LinePolygonUtilsControllerIntegrationTest {
    private static final String URL = "http://localhost:";
    private static final String POLYGON_GEOMETRY = "POLYGON((1 1,2 1,2 2,1 1))";
    private static final DSLContext dsl = getConnection();
    @LocalServerPort
    private int port;
    private final TestRestTemplate restTemplate = new TestRestTemplate();

    @Test
    public void isIntersectsTest() {
        long lineId = addLineToDb("LINESTRING(2.5 1, 1 1)");
        long polygonId = addPolygonToDb();
        URI uri = getUri("intersects", lineId, polygonId);
        ResponseEntity<Boolean> responseEntity = restTemplate.getForEntity(uri, boolean.class);
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        assertThat(responseEntity.getBody(), is(true));
    }

    @Test
    public void isWithInTest() {
        long lineId = addLineToDb("LINESTRING(1.7 1.6, 1.8 1.1)");
        long polygonId = addPolygonToDb();
        URI uri = getUri("within", lineId, polygonId);
        ResponseEntity<Boolean> responseEntity = restTemplate.getForEntity(uri, boolean.class);
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        assertThat(responseEntity.getBody(), is(true));
    }

    @Test
    public void getDistanceTest() {
        double distance = 110576.41652415;
        long lineId = addLineToDb("LINESTRING(3 3,2 3)");
        long polygonId = addPolygonToDb();
        URI uri = getUri("distance", lineId, polygonId);
        ResponseEntity<Double> responseEntity = restTemplate.getForEntity(uri, double.class);
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        assertThat(responseEntity.getBody(), is(distance));
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
                .values(field(val("polygon")), stGeomFromText(POLYGON_GEOMETRY), stArea(POLYGON_GEOMETRY))
                .returningResult(POLYGON.ID)
                .fetchOne()
                .into(long.class);
    }

    private long addLineToDb(String line) {
        return dsl.insertInto(LINE, LINE.NAME, LINE.GEOMETRY, LINE.LENGTH)
                .values(field(val("name")), stGeomFromText(line), stLength(line))
                .returningResult(LINE.ID)
                .fetchOne()
                .into(long.class);
    }

    private URI getUri(String url, long lineId, long polygonId) {
        return UriComponentsBuilder.fromHttpUrl(URL + port + url)
                .queryParam("lineId", lineId)
                .queryParam("polygonId", polygonId)
                .build()
                .toUri();
    }
}
