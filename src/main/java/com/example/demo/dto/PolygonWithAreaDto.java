package com.example.demo.dto;

import com.example.demo.entity.Point;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Value;

import java.util.List;

@Value
public class PolygonWithAreaDto {
    @JsonUnwrapped
    PolygonDto polygonDto;
    double area;

    public PolygonWithAreaDto(String name, List<Point> points, double area) {
        this.polygonDto = new PolygonDto(name, points);
        this.area = area;
    }
}
