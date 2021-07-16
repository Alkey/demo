package com.example.demo.dto;

import com.example.demo.entity.Point;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class LineWithLengthDto {
    String name;
    Point startPoint;
    Point endPoint;
    double length;

    @JsonCreator
    public LineWithLengthDto(@JsonProperty("name") String name,
                             @JsonProperty("startPoint") Point startPoint,
                             @JsonProperty("endPoint") Point endPoint,
                             @JsonProperty("length") double length) {
        this.name = name;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.length = length;
    }
}
