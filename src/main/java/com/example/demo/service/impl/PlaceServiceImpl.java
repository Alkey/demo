package com.example.demo.service.impl;

import com.example.demo.dto.PlaceDto;
import com.example.demo.dto.PlaceWithLengthDto;
import com.example.demo.entity.Place;
import com.example.demo.entity.Point;
import com.example.demo.repository.PlaceRepository;
import com.example.demo.service.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlaceServiceImpl implements PlaceService {
    private final PlaceRepository placeRepository;

    @Override
    public boolean add(PlaceDto dto) {
        Place place = new Place();
        place.setName(dto.getName());
        place.setLocation(dto.toString());
        return placeRepository.add(place) == 1;
    }

    @Override
    public PlaceWithLengthDto get(long id) {
        Place place = placeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Incorrect id"));
        List<Double> coordinatesList = getCoordinatesList(place.getLocation());
        Point startPoint = new Point(coordinatesList.get(0), coordinatesList.get(1));
        Point endPoint = new Point(coordinatesList.get(2), coordinatesList.get(3));
        return new PlaceWithLengthDto(place.getName(), startPoint, endPoint, place.getLength());
    }

    private List<Double> getCoordinatesList(String lineString) {
        return Arrays.stream(lineString.replaceAll("[\\[\\]]", "").split(","))
                .map(Double::parseDouble)
                .collect(Collectors.toList());
    }
}
