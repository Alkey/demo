package com.example.demo.dto;

import com.example.demo.entity.Point;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.util.List;

@Value
public class PolygonWithAreaDto {
    String name;
    List<List<Point>> points;
    double area;

    @JsonCreator
    public PolygonWithAreaDto(@JsonProperty("name") String name,
                              @JsonProperty("points") List<List<Point>> points,
                              @JsonProperty("area") double area) {
        this.name = name;
        this.points = points;
        this.area = area;
    }
}
