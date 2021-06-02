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
    private final LineRepository placeRepository;
    private final ObjectMapper mapper;

    @Override
    public boolean add(LineDto dto) {
        return placeRepository.add(dto.getName(), dto.toString()) > 0;
    }

    @Override
    public Optional<LineWithLengthDto> findById(long id) throws JsonProcessingException {
        Optional<Line> optionalPlace = placeRepository.findById(id);
        if (optionalPlace.isEmpty()) {
            return Optional.empty();
        }
        Line place = optionalPlace.get();
        List<Point> points = getPoints(place.getLocation());
        if (points.size() != 2) {
            return Optional.empty();
        }
        return Optional.of(new LineWithLengthDto(place.getName(), points.get(0), points.get(1), place.getLength()));
    }

    private List<Point> getPoints(String line) throws JsonProcessingException {
        return mapper.readValue(line, new TypeReference<List<List<Double>>>() {}).stream()
                .filter(coordinates -> coordinates.size() == 2)
                .map(coordinates -> new Point(coordinates.get(0), coordinates.get(1)))
                .collect(Collectors.toUnmodifiableList());
    }
}
