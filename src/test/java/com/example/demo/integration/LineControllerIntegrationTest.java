package com.example.demo.integration;

import com.example.demo.dto.LineWithLengthDto;
import com.example.demo.entity.GeoJsonLineGeometry;
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
import static com.example.demo.jooq.sample.model.tables.Line.LINE;
import static com.example.demo.util.PostGisUtil.stGeomFromText;
import static com.example.demo.util.PostGisUtil.stLength;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.val;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LineControllerIntegrationTest {
    private static final long INCORRECT_ID = 1;
    private static final String LOCATION = "LINESTRING(1 0,1 1)";
    private static final String NAME = "line";
    private static final String NAME_SECOND = "second_line";
    private static final String LOCATION_SECOND = "LINESTRING(1 0,2 2)";
    private static final String URL = "http://localhost:";
    private static final DSLContext dsl = getConnection();
    @LocalServerPort
    private int port;
    private final TestRestTemplate restTemplate = new TestRestTemplate();

    @Test
    public void addLineTest() {
        GeoJsonLineGeometry geometry = new GeoJsonLineGeometry(List.of(List.of(0.0, 1.0), List.of(1.0, 1.0)));
        ResponseEntity<Void> responseEntity = restTemplate.postForEntity(URL + port + "line", geometry, Void.class);
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void getLineTest() {
        Long id = dsl.insertInto(LINE, LINE.NAME, LINE.GEOMETRY, LINE.LENGTH)
                .values(field(val(NAME)), stGeomFromText(LOCATION), stLength(LOCATION))
                .returningResult(LINE.ID)
                .fetchOne()
                .into(long.class);
        LineWithLengthDto dto = new LineWithLengthDto(NAME,
                new Point(1, 0),
                new Point(1, 1), 110574.38855779878);
        ResponseEntity<LineWithLengthDto> responseEntity = restTemplate.getForEntity(URL + port + "line/" + id,
                LineWithLengthDto.class);
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        assertThat(responseEntity.getBody(), is(dto));
    }

    @Test
    public void getNotExistLineTest() {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(URL + port + "line/" + INCORRECT_ID, String.class);
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void getLineIntersectionTest() {
        Point point = new Point(1, 0);
        List<Long> lineIds = dsl.insertInto(LINE, LINE.NAME, LINE.GEOMETRY, LINE.LENGTH)
                .values(field(val(NAME)), stGeomFromText(LOCATION), stLength(LOCATION))
                .values(field(val(NAME_SECOND)), stGeomFromText(LOCATION_SECOND), stLength(LOCATION_SECOND))
                .returningResult(LINE.ID)
                .fetch()
                .into(long.class);
        URI uri = UriComponentsBuilder.fromHttpUrl(URL + port + "line/intersection")
                .queryParam("firstId", lineIds.get(0))
                .queryParam("secondId", lineIds.get(1))
                .build()
                .toUri();
        ResponseEntity<Point> responseEntity = restTemplate.getForEntity(uri, Point.class);
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        assertThat(responseEntity.getBody(), is(point));
    }

    @Test
    public void getLineIntersectionReturnOptionalEmptyTest() {
        URI uri = UriComponentsBuilder.fromHttpUrl(URL + port + "line/intersection")
                .queryParam("firstId", INCORRECT_ID)
                .queryParam("secondId", INCORRECT_ID)
                .build()
                .toUri();
        ResponseEntity<Point> responseEntity = restTemplate.getForEntity(uri, Point.class);
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.BAD_REQUEST));
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
