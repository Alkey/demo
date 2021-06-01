package com.example.demo.dto;

import com.example.demo.entity.Point;
import lombok.Value;

@Value
public class PlaceWithLengthDto {
    String name;
    Point startPoint;
    Point endPoint;
    double length;
}
