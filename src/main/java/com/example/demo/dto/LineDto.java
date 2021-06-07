package com.example.demo.dto;

import com.example.demo.entity.Point;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class LineDto {
    String name;
    Point startPoint;
    Point endPoint;

    @JsonCreator
    public LineDto(@JsonProperty("name") String name,
                   @JsonProperty("startPoint") Point startPoint,
                   @JsonProperty("endPoint") Point endPoint) {
        this.name = name;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }

    @Override
    public String toString() {
        return "LINESTRING(" + startPoint.toString() + ", "
                + endPoint.toString() + ")";
    }
}
