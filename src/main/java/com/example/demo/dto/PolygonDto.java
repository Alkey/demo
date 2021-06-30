package com.example.demo.dto;

import com.example.demo.entity.Point;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import javax.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;

@Value
public class PolygonDto {
    String name;
    @Size(min = 1)
    List<@Size(min = 4) List<Point>> points;

    @JsonCreator
    public PolygonDto(@JsonProperty("name") String name,
                      @JsonProperty("points") List<List<Point>> points) {
        this.name = name;
        this.points = points;
    }

    @Override
    public String toString() {
        return points.stream()
                .map(this::getRing)
                .collect(Collectors.joining(",", "POLYGON(", ")"));
    }

    private String getRing(List<Point> points) {
        return points.stream()
                .map(Point::toString)
                .collect(Collectors.joining(",", "(", ")"));
    }
}
