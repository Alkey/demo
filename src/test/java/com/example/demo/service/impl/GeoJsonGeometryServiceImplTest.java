package com.example.demo.service.impl;

import com.example.demo.entity.GeoJsonLineGeometry;
import com.example.demo.entity.GeoJsonPolygonGeometry;
import com.example.demo.service.BackupGeometryDataService;
import com.example.demo.service.LineService;
import com.example.demo.service.PolygonService;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;

public class GeoJsonGeometryServiceImplTest {
    private final LineService lineService = mock(LineService.class);
    private final PolygonService polygonService = mock(PolygonService.class);
    private final BackupGeometryDataService backupGeometryDataService = mock(BackupGeometryDataService.class);
    private final GeoJsonGeometryServiceImpl service = new GeoJsonGeometryServiceImpl(lineService,
            polygonService, backupGeometryDataService);

    @Test
    public void shouldReturnTrueWhenGeometryIsLineString() {
        GeoJsonLineGeometry lineString = new GeoJsonLineGeometry(List.of(
                List.of(28.494243622, 49.283259954),
                List.of(28.500595093, 49.279004601)));
        when(lineService.add(lineString.toEntity())).thenReturn(true);

        assertThat(service.add(lineString), is(true));

        verify(lineService).add(lineString.toEntity());
    }

    @Test
    public void shouldReturnTrueWhenGeometryIsPolygon() {
        GeoJsonPolygonGeometry polygon = new GeoJsonPolygonGeometry(List.of(List.of(
                List.of(28.507118225, 49.267132467),
                List.of(28.510894775, 49.260355109),
                List.of(28.516817093, 49.264612071),
                List.of(28.507118225, 49.267132467))));
        when(polygonService.add(polygon.toEntity())).thenReturn(true);

        assertThat(service.add(polygon), is(true));

        verify(polygonService).add(polygon.toEntity());
    }
}