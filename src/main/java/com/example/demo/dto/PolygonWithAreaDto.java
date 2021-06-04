package com.example.demo.dto;

import com.example.demo.entity.Hole;
import com.example.demo.entity.Point;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Value;

import java.util.List;

@Value
public class PolygonWithAreaDto {
    @JsonUnwrapped
    PolygonDto polygonDto;
    double area;

    public PolygonWithAreaDto(String name, List<Point> points, List<Hole> holes, double area) {
        this.polygonDto = new PolygonDto(name, points, holes);
        this.area = area;
    }
}
