package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Value
public class Hole {
    List<Point> points;

    @JsonCreator
    public Hole(@JsonProperty("points") List<Point> points) {
        this.points = points;
    }

    @Override
    public String toString() {
        if (points == null || points.isEmpty()) {
            return "";
        }
        return Stream.builder()
                .add("(")
                .add(points.stream()
                        .limit(points.size() - 1)
                        .map(p -> p.toString() + ", ")
                        .collect(Collectors.joining()))
                .add(points.get(points.size() - 1).toString())
                .add(")")
                .build()
                .map(p -> (String) p)
                .collect(Collectors.joining());
    }
}
