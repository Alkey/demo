package com.example.demo.service.impl;

import com.example.demo.dto.PolygonDto;
import com.example.demo.dto.PolygonWithAreaDto;
import com.example.demo.entity.Hole;
import com.example.demo.entity.Point;
import com.example.demo.entity.Polygon;
import com.example.demo.repository.PolygonRepository;
import com.example.demo.service.PolygonService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
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
            List<List<List<Double>>> polygonPoints = mapper.readValue(polygon.getGeometry(), new TypeReference<>() {});
            List<Point> points = getPolygonPoints(polygonPoints);
            List<Hole> holes = getHoles(polygonPoints);
            return Optional.of(new PolygonWithAreaDto(polygon.getName(), points, holes, polygon.getArea()));
        }
        return Optional.empty();
    }

    private List<Point> getPolygonPoints(List<List<List<Double>>> polygonPoints) {
        return polygonPoints.stream()
                .limit(1)
                .map(this::getPoints)
                .flatMap(Collection::stream)
                .collect(Collectors.toUnmodifiableList());
    }

    private List<Hole> getHoles(List<List<List<Double>>> polygonPoints) {
        return polygonPoints.stream()
                .skip(1L)
                .map(this::getPoints)
                .map(Hole::new)
                .collect(Collectors.toUnmodifiableList());
    }

    private List<Point> getPoints(List<List<Double>> points) {
        return points.stream()
                .filter(coordinates -> coordinates.size() == 2)
                .map(coordinates -> new Point(coordinates.get(0), coordinates.get(1)))
                .collect(Collectors.toUnmodifiableList());
    }
}
