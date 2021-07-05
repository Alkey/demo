package com.example.demo.dto;

import com.example.demo.entity.Point;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.util.List;

@Value
public class PolygonWithAreaDto {
    PolygonDto polygonDto;
    double area;

    @JsonCreator
    public PolygonWithAreaDto(@JsonProperty("name") String name,
                              @JsonProperty("points") List<List<Point>> points,
                              @JsonProperty("area") double area) {
        this.polygonDto = new PolygonDto(name, points);
        this.area = area;
    }
}
