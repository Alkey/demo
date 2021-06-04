package com.example.demo.dto;

import com.example.demo.entity.Hole;
import com.example.demo.entity.Point;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import javax.validation.constraints.Size;
import java.util.List;

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
        StringBuilder builder = new StringBuilder("POLYGON((");
        points.forEach(p -> builder.append(p.getLat())
                .append(" ")
                .append(p.getLon())
                .append(", "));
        builder.replace(builder.length() - 2, builder.length(), "")
                .append(")");
        holes.forEach(h -> builder.append(h.toString())
                .append(","));
        return builder.deleteCharAt(builder.length() - 1)
                .append(")").toString();
    }
}
