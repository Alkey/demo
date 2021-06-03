package com.example.demo.service.impl;

import com.example.demo.dto.PolygonDto;
import com.example.demo.dto.PolygonWithAreaDto;
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
        List<Point> points = dto.getPoints();
        if (points.get(0).equals(points.get(points.size() - 1))) {
            return repository.add(dto.getName(), dto.toString()) > 0;
        }
        return false;
    }

    @Override
    public Optional<PolygonWithAreaDto> findById(long id) throws JsonProcessingException {
        Optional<Polygon> optionalPolygon = repository.findById(id);
        if (optionalPolygon.isPresent()) {
            Polygon polygon = optionalPolygon.get();
            List<Point> points = getPoints(polygon.getLocation());
            return Optional.of(new PolygonWithAreaDto(polygon.getName(), points, polygon.getArea()));
        }
        return Optional.empty();
    }

    private List<Point> getPoints(String polygon) throws JsonProcessingException {
        return mapper.readValue(polygon, new TypeReference<List<List<List<Double>>>>() {}).stream()
                .flatMap(Collection::stream)
                .filter(coordinates -> coordinates.size() == 2)
                .map(coordinates -> new Point(coordinates.get(0), coordinates.get(1)))
                .collect(Collectors.toUnmodifiableList());
    }
}
