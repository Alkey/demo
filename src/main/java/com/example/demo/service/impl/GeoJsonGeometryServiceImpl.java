package com.example.demo.service.impl;

import com.example.demo.entity.FeatureCollection;
import com.example.demo.entity.GeoJsonGeometry;
import com.example.demo.entity.GeoJsonLineGeometry;
import com.example.demo.entity.GeoJsonPolygonGeometry;
import com.example.demo.service.BackupGeometryDataService;
import com.example.demo.service.GeoJsonGeometryService;
import com.example.demo.service.LineService;
import com.example.demo.service.PolygonService;
import com.example.demo.util.Count;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GeoJsonGeometryServiceImpl implements GeoJsonGeometryService {
    private static final String COUNTER_NAME = "geometry_count";
    private final LineService lineService;
    private final PolygonService polygonService;
    private final BackupGeometryDataService backupGeometryDataService;

    @Count(counterName = COUNTER_NAME)
    @Override
    public boolean add(GeoJsonGeometry geometry) {
        if (geometry.getType().equalsIgnoreCase("linestring")) {
            return lineService.add(((GeoJsonLineGeometry) geometry).toEntity());
        } else if (geometry.getType().equalsIgnoreCase("polygon")) {
            return polygonService.add(((GeoJsonPolygonGeometry) geometry).toEntity());
        }
        return false;
    }

    public boolean restore() {
        return backupGeometryDataService.restoreGeometries();
    }

    @Override
    public FeatureCollection getContainedInPolygonGeometries(GeoJsonPolygonGeometry geometry) {
        String polygon = geometry.toEntity().toString();
        List<GeoJsonGeometry> geometries = lineService.getContainedInPolygonLines(polygon);
        geometries.addAll(polygonService.getContainedInPolygonGeometries(polygon));
        FeatureCollection collection = new FeatureCollection();
        collection.setFeatures(geometries);
        collection.setId(collection.hashCode());
        return collection;
    }
}
