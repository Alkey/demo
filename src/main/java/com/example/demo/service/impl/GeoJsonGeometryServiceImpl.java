package com.example.demo.service.impl;

import com.example.demo.entity.GeoJsonGeometry;
import com.example.demo.entity.GeoJsonLineGeometry;
import com.example.demo.entity.GeoJsonPolygonGeometry;
import com.example.demo.entity.Point;
import com.example.demo.repository.GeoJsonGeometryRepository;
import com.example.demo.service.GeoJsonGeometryService;
import com.example.demo.service.LineService;
import com.example.demo.service.PolygonService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GeoJsonGeometryServiceImpl implements GeoJsonGeometryService {
    private final GeoJsonGeometryRepository repository;
    private final LineService lineService;
    private final PolygonService polygonService;
    private final ObjectMapper mapper;

    @Override
    public boolean add(GeoJsonGeometry geometry) {
        if (geometry.getType().equalsIgnoreCase("linestring")) {
            return lineService.add(((GeoJsonLineGeometry) geometry).toEntity());
        } else if (geometry.getType().equalsIgnoreCase("polygon")) {
            return polygonService.add(((GeoJsonPolygonGeometry) geometry).toEntity());
        }
        return false;
    }

    @Override
    public Optional<Boolean> isIntersects(long lineId, long polygonId) {
        return repository.isIntersects(lineId, polygonId);
    }

    @Override
    public Optional<Boolean> isWithIn(long lineId, long polygonId) {
        return repository.isWithIn(lineId, polygonId);
    }

    @Override
    public Optional<Double> getDistance(long lineId, long polygonId) {
        return repository.getDistance(lineId, polygonId);
    }

    @Override
    public GeoJsonGeometry getPolygonIntersection(long firstPolygonId, long secondPolygonId) throws JsonProcessingException {
        return mapper.readValue(repository.getPolygonIntersection(firstPolygonId, secondPolygonId), GeoJsonGeometry.class);
    }

    @Override
    public Optional<Point> getLineStringIntersection(long firstLineId, long secondLineId) throws JsonProcessingException {
        return getPoint(repository.getLineStringIntersection(firstLineId, secondLineId));
    }

    private Optional<Point> getPoint(String geometry) throws JsonProcessingException {
        List<Double> coordinates = mapper.readValue(geometry, new TypeReference<>() {
        });
        if (coordinates.size() >= 2) {
            return Optional.of(new Point(coordinates.get(0), coordinates.get(1)));
        }
        return Optional.empty();
    }
}
