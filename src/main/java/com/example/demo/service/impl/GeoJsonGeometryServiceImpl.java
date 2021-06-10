package com.example.demo.service.impl;

import com.example.demo.entity.GeoJsonGeometry;
import com.example.demo.entity.GeoJsonLineGeometry;
import com.example.demo.entity.GeoJsonPolygonGeometry;
import com.example.demo.service.GeoJsonGeometryService;
import com.example.demo.service.LineService;
import com.example.demo.service.PolygonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GeoJsonGeometryServiceImpl implements GeoJsonGeometryService {
    private final LineService lineService;
    private final PolygonService polygonService;

    @Override
    public boolean add(GeoJsonGeometry geometry) {
        if (geometry.getType().equalsIgnoreCase("linestring")) {
            return lineService.add(((GeoJsonLineGeometry) geometry).toEntity());
        } else if (geometry.getType().equalsIgnoreCase("polygon")) {
            return polygonService.add(((GeoJsonPolygonGeometry) geometry).toEntity());
        }
        return false;
    }
}
