package com.example.demo.service.impl;

import com.example.demo.entity.GeoJsonGeometry;
import com.example.demo.entity.LineGeometry;
import com.example.demo.entity.PolygonGeometry;
import com.example.demo.service.GeometryService;
import com.example.demo.service.LineService;
import com.example.demo.service.PolygonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GeometryServiceImpl implements GeometryService {
    private final LineService lineService;
    private final PolygonService polygonService;

    @Override
    public boolean add(GeoJsonGeometry geometry) {
        if (geometry.getType().equalsIgnoreCase("linestring")) {
            return lineService.add(((LineGeometry) geometry).toEntity());
        } else if (geometry.getType().equalsIgnoreCase("polygon")) {
            return polygonService.add(((PolygonGeometry) geometry).toEntity());
        }
        return false;
    }
}
