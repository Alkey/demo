package com.example.demo.dto;

import com.example.demo.entity.Hole;
import com.example.demo.entity.Point;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import javax.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Value
public class PolygonDto {
    String name;
    @Size(min = 3)
    List<Point> points;
    List<Hole> holes;

    @JsonCreator
    public PolygonDto(@JsonProperty("name") String name,
                      @JsonProperty("points") List<Point> points,
                      @JsonProperty("holes") List<Hole> holes) {
        this.name = name;
        this.points = points;
        this.holes = holes;
    }

    @Override
    public String toString() {
        if (holes != null && holes.size() > 0) {
            return Stream.builder()
                    .add("POLYGON((")
                    .add(points.stream()
                            .limit(points.size() - 1)
                            .map(p -> p.toString() + ", ")
                            .collect(Collectors.joining()))
                    .add(points.get(points.size() - 1).toString())
                    .add("),")
                    .add(holes.stream()
                            .limit(holes.size() - 1)
                            .map(h -> h.toString() + ",")
                            .collect(Collectors.joining()))
                    .add(holes.get(holes.size() - 1).toString())
                    .add(")")
                    .build()
                    .map(p -> (String) p)
                    .collect(Collectors.joining());
        }
        return Stream.builder()
                .add("POLYGON((")
                .add(points.stream()
                        .limit(points.size() - 1)
                        .map(p -> p.toString() + ", ")
                        .collect(Collectors.joining()))
                .add(points.get(points.size() - 1).toString())
                .add("))")
                .build()
                .map(p -> (String) p)
                .collect(Collectors.joining());
    }
}
