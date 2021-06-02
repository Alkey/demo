package com.example.demo.dto;

import com.example.demo.entity.Point;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PlaceWithLengthDto {
    private final PlaceDto placeDto;
    private final double length;

    public PlaceWithLengthDto(String name, Point startPoint, Point endPoint, double length) {
        this.placeDto = new PlaceDto(name, startPoint, endPoint);
        this.length = length;
    }
}
