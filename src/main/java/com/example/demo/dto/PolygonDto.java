package com.example.demo.dto;

import com.example.demo.entity.Point;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.util.List;

@Value
public class PolygonDto {
    String name;
    List<Point> points;

    @JsonCreator
    public PolygonDto(@JsonProperty("name") String name,
                      @JsonProperty("points") List<Point> points) {
        this.name = name;
        this.points = points;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("POLYGON((");
        for (int i = 0; i < points.size(); i++) {
            builder.append(points.get(i).getLat())
                    .append(" ")
                    .append(points.get(i).getLon());
            if (i + 1 < points.size()) {
                builder.append(", ");
            } else {
                builder.append("))");
            }
        }
        return builder.toString();
    }
}
