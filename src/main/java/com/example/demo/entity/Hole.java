package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.util.List;

@Value
public class Hole {
    List<Point> points;

    @JsonCreator
    public Hole(@JsonProperty("points") List<Point> points) {
        this.points = points;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(",(");
        points.forEach(p -> builder.append(p.getLat())
                .append(" ")
                .append(p.getLon())
                .append(", "));
        return builder.replace(builder.length() - 2, builder.length(), "")
                .append(")")
                .toString();
    }
}
