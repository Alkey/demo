package com.example.demo.service.impl;

import com.example.demo.entity.GeoJsonGeometry;
import com.example.demo.entity.GeoJsonLineGeometry;
import com.example.demo.entity.GeoJsonPolygonGeometry;
import com.example.demo.service.GeoJsonGeometryService;
import com.example.demo.service.LineService;
import com.example.demo.service.PolygonService;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GeoJsonGeometryServiceImpl implements GeoJsonGeometryService {
    private static final String COUNTER_NAME = "geometry_count";
    private final LineService lineService;
    private final PolygonService polygonService;
    private final MeterRegistry registry;

    @Override
    public boolean add(GeoJsonGeometry geometry) {
        if (geometry.getType().equalsIgnoreCase("linestring")
                && lineService.add(((GeoJsonLineGeometry) geometry).toEntity())) {
            registry.counter(COUNTER_NAME).increment();
            return true;
        } else if (geometry.getType().equalsIgnoreCase("polygon")
                && polygonService.add(((GeoJsonPolygonGeometry) geometry).toEntity())) {
            registry.counter(COUNTER_NAME).increment();
            return true;
        }
        return false;
    }
}
