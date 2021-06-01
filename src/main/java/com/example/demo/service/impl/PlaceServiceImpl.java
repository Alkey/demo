package com.example.demo.service.impl;

import com.example.demo.dto.PlaceDto;
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
    public double add(PlaceDto dto) {
        Place place = new Place();
        place.setName(dto.getName());
        place.setLocation(dto.toString());
        return placeRepository.add(place);
    }

    @Override
    public PlaceDto get(long id) {
        Place place = placeRepository.get(id);
        List<Double> collect = Arrays.stream(place.getLocation().replaceAll("[a-zA-Z(),]", " ").trim().split(" "))
                .map(Double::parseDouble)
                .collect(Collectors.toList());
        Point startPoint = new Point(collect.get(0), collect.get(1));
        Point endPoint = new Point(collect.get(2), collect.get(3));
        return new PlaceDto(place.getName(), startPoint, endPoint, place.getLength());
    }
}
