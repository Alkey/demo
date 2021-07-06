package com.example.demo.integration;

import com.example.demo.dto.PolygonDto;
import com.example.demo.dto.PolygonWithAreaDto;
import com.example.demo.entity.GeoJsonGeometry;
import com.example.demo.entity.GeoJsonPolygonGeometry;
import com.example.demo.entity.Point;
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
import java.util.List;

import static com.example.demo.ConnectionUtils.getConnection;
import static com.example.demo.jooq.sample.model.tables.Polygon.POLYGON;
import static com.example.demo.util.PostGisUtil.stArea;
import static com.example.demo.util.PostGisUtil.stGeomFromText;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.val;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PolygonControllerIntegrationTest {
    private static final List<List<Point>> POINTS = List.of(List.of(
            new Point(1, 1),
            new Point(2, 1),
            new Point(2, 2),
            new Point(1, 1)));
    private static final String GEOMETRY = "POLYGON((1 1,2 1,2 2,1 1))";
    private static final String NAME = "polygon";
    private static final String URL = "http://localhost:";
    private static final long INCORRECT_ID = 1;
    private static final DSLContext dsl = getConnection();
    @LocalServerPort
    private int port;
    private final TestRestTemplate restTemplate = new TestRestTemplate();

    @Test
    public void addPolygonTest() {
        PolygonDto dto = new PolygonDto(NAME, POINTS);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(URL + port + "polygon", dto, String.class);
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void getPolygonTest() {
        double area = 6153961068.605661;
        long id = dsl.insertInto(POLYGON, POLYGON.NAME, POLYGON.GEOMETRY, POLYGON.AREA)
                .values(field(val(NAME)), stGeomFromText(GEOMETRY), stArea(GEOMETRY))
                .returningResult(POLYGON.ID)
                .fetchOne()
                .into(long.class);
        PolygonWithAreaDto dto = new PolygonWithAreaDto(NAME, POINTS, area);
        ResponseEntity<PolygonWithAreaDto> responseEntity = restTemplate.getForEntity(URL + port + "polygon/" + id,
                PolygonWithAreaDto.class);
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        assertThat(responseEntity.getBody(), is(dto));
    }

    @Test
    public void getNotExistPolygonTest() {
        HttpStatus status = restTemplate.getForEntity(URL + port + "polygon/" + INCORRECT_ID,
                PolygonWithAreaDto.class)
                .getStatusCode();
        assertThat(status, is(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void getIntersectionTest() {
        String geometry = "POLYGON((2 1.5,1 1.5,1 0.5,2 1.5))";
        GeoJsonGeometry polygon = new GeoJsonPolygonGeometry(List.of(
                List.of(
                        List.of(1.5, 1.0),
                        List.of(1.0, 1.0),
                        List.of(1.5, 1.5),
                        List.of(2.0, 1.5),
                        List.of(1.5, 1.0))));
        List<Long> ids = dsl.insertInto(POLYGON, POLYGON.NAME, POLYGON.GEOMETRY, POLYGON.AREA)
                .values(field(val(NAME)), stGeomFromText(GEOMETRY), stArea(GEOMETRY))
                .values(field(val(NAME)), stGeomFromText(geometry), stArea(geometry))
                .returningResult(POLYGON.ID)
                .fetch()
                .into(long.class);
        URI uri = UriComponentsBuilder.fromHttpUrl(URL + port + "polygon/intersection")
                .queryParam("firstId", ids.get(0))
                .queryParam("secondId", ids.get(1))
                .build()
                .toUri();
        ResponseEntity<GeoJsonGeometry> responseEntity = restTemplate.getForEntity(uri, GeoJsonGeometry.class);
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        assertThat(responseEntity.getBody(), is(polygon));
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