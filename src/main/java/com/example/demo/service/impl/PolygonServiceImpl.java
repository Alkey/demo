package com.example.demo.service.impl;

import com.example.demo.dto.PolygonDto;
import com.example.demo.dto.PolygonWithAreaDto;
import com.example.demo.entity.GeoJsonGeometry;
import com.example.demo.entity.Point;
import com.example.demo.entity.Polygon;
import com.example.demo.repository.PolygonRepository;
import com.example.demo.service.PolygonService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PolygonServiceImpl implements PolygonService {
    private final PolygonRepository repository;
    private final ObjectMapper mapper;

    @Override
    public boolean add(PolygonDto dto) {
        return repository.add(dto.getName(), dto.toString()) > 0;
    }

    @Override
    public Optional<PolygonWithAreaDto> findById(long id) throws JsonProcessingException {
        Optional<Polygon> optionalPolygon = repository.findById(id);
        if (optionalPolygon.isPresent()) {
            Polygon polygon = optionalPolygon.get();
            return Optional.of(new PolygonWithAreaDto(polygon.getName(), getPolygonPoints(polygon.getGeometry()), polygon.getArea()));
        }
        return Optional.empty();
    }

    @Override
    public GeoJsonGeometry getPolygonIntersection(long firstPolygonId, long secondPolygonId) throws JsonProcessingException {
        return mapper.readValue(repository.getPolygonIntersection(firstPolygonId, secondPolygonId), GeoJsonGeometry.class);
    }

    private List<List<Point>> getPolygonPoints(String geometry) throws JsonProcessingException {
        List<List<List<Double>>> polygonPoints = mapper.readValue(geometry, new TypeReference<>() {});
        return polygonPoints.stream()
                .map(this::getPoints)
                .collect(Collectors.toUnmodifiableList());
    }

    private List<Point> getPoints(List<List<Double>> points) {
        return points.stream()
                .filter(coordinates -> coordinates.size() >= 2)
                .map(coordinates -> new Point(coordinates.get(0), coordinates.get(1)))
                .collect(Collectors.toUnmodifiableList());
    }
}
