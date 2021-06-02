package com.example.demo.dto;

import com.example.demo.entity.Point;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Value;

@Value
public class LineWithLengthDto {
    @JsonUnwrapped
    LineDto lineDto;
    double length;

    public LineWithLengthDto(String name, Point startPoint, Point endPoint, double length) {
        this.lineDto = new LineDto(name, startPoint, endPoint);
        this.length = length;
    }
}
