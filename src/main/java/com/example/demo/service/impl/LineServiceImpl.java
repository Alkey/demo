package com.example.demo.service.impl;

import com.example.demo.dto.LineDto;
import com.example.demo.dto.LineWithLengthDto;
import com.example.demo.entity.Line;
import com.example.demo.entity.Point;
import com.example.demo.repository.LineRepository;
import com.example.demo.service.LineService;
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
public class LineServiceImpl implements LineService {
    private final LineRepository repository;
    private final ObjectMapper mapper;

    @Override
    public boolean add(LineDto dto) {
        return repository.add(dto.getName(), dto.toString()) > 0;
    }

    @Override
    public Optional<LineWithLengthDto> findById(long id) throws JsonProcessingException {
        Optional<Line> optionalPlace = repository.findById(id);
        if (optionalPlace.isPresent()) {
            Line place = optionalPlace.get();
            List<Point> points = getPoints(place.getGeometry());
            if (points.size() == 2) {
                return Optional.of(new LineWithLengthDto(place.getName(), points.get(0), points.get(1), place.getLength()));
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<Point> getLineStringIntersection(long firstLineId, long secondLineId) throws JsonProcessingException {
        return getPoint(repository.getLineStringIntersection(firstLineId, secondLineId));
    }

    private Optional<Point> getPoint(String geometry) throws JsonProcessingException {
        List<Double> coordinates = mapper.readValue(geometry, new TypeReference<>() {});
        if (coordinates.size() >= 2) {
            return Optional.of(new Point(coordinates.get(0), coordinates.get(1)));
        }
        return Optional.empty();
    }

    private List<Point> getPoints(String line) throws JsonProcessingException {
        return mapper.readValue(line, new TypeReference<List<List<Double>>>() {}).stream()
                .filter(coordinates -> coordinates.size() >= 2)
                .map(coordinates -> new Point(coordinates.get(0), coordinates.get(1)))
                .collect(Collectors.toUnmodifiableList());
    }
}
