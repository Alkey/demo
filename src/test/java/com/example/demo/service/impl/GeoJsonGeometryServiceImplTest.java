package com.example.demo.service.impl;

import com.example.demo.entity.GeoJsonLineGeometry;
import com.example.demo.entity.GeoJsonPolygonGeometry;
import com.example.demo.service.BackupGeometryDataService;
import com.example.demo.service.LineService;
import com.example.demo.service.PolygonService;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GeoJsonGeometryServiceImplTest {
    private final LineService lineService = mock(LineService.class);
    private final PolygonService polygonService = mock(PolygonService.class);
    private final BackupGeometryDataService backupGeometryDataService = mock(BackupGeometryDataService.class);
    private final GeoJsonGeometryServiceImpl service = new GeoJsonGeometryServiceImpl(lineService,
            polygonService, backupGeometryDataService);
    private final Random random = new Random();

    @Test
    public void addLineStringGeometryTest() {
        GeoJsonLineGeometry lineString = new GeoJsonLineGeometry(List.of(
                getListWithRandomDoubles(),
                getListWithRandomDoubles()));
        when(lineService.add(lineString)).thenReturn(true);

        assertThat(service.add(lineString), is(true));
    }

    @Test
    public void addPolygonGeometryTest() {
        GeoJsonPolygonGeometry polygon = new GeoJsonPolygonGeometry(List.of(List.of(
                getListWithRandomDoubles(),
                getListWithRandomDoubles(),
                getListWithRandomDoubles(),
                getListWithRandomDoubles())));
        when(polygonService.add(polygon)).thenReturn(true);

        assertThat(service.add(polygon), is(true));
    }

    private List<Double> getListWithRandomDoubles() {
        return List.of(random.nextDouble(), random.nextDouble());
    }
}
