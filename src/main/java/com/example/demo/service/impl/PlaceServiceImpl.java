package com.example.demo.service.impl;

import com.example.demo.dto.PlaceDto;
import com.example.demo.dto.PlaceWithLengthDto;
import com.example.demo.entity.Place;
import com.example.demo.entity.Point;
import com.example.demo.repository.PlaceRepository;
import com.example.demo.service.PlaceService;
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
public class PlaceServiceImpl implements PlaceService {
    private final PlaceRepository placeRepository;
    private final ObjectMapper mapper;

    @Override
    public boolean add(PlaceDto dto) {
        return placeRepository.add(dto.getName(), dto.toString()) > 0;
    }

    @Override
    public Optional<PlaceWithLengthDto> findById(long id) {
        Optional<Place> optionalPlace = placeRepository.findById(id);
        if (optionalPlace.isEmpty()) {
            return Optional.empty();
        }
        Place place = optionalPlace.get();
        List<List<Double>> coordinates;
        try {
            coordinates = mapper.readValue(place.getLocation(), new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Incorrect coordinates");
        }
        List<Point> points = coordinates.stream()
                .map(c -> new Point(c.get(0), c.get(1)))
                .collect(Collectors.toList());
        return Optional.of(new PlaceWithLengthDto(place.getName(), points.get(0), points.get(1), place.getLength()));
    }
}
