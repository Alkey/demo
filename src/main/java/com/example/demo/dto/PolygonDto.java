package com.example.demo.dto;

import com.example.demo.entity.Point;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import javax.validation.constraints.Size;
import java.util.List;

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
        StringBuilder builder = new StringBuilder("POLYGON(");
        points.forEach(pointsList -> getRing(builder, pointsList));
        return builder.replace(builder.length() - 1, builder.length(), ")").toString();
    }

    private void getRing(StringBuilder builder, List<Point> points) {
        builder.append("(");
        points.forEach(point -> builder.append(point.toString())
                .append(", "));
        builder.replace(builder.length() - 2, builder.length(), "")
                .append("),");
    }
}
