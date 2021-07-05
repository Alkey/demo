package com.example.demo.dto;

import com.example.demo.entity.Point;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class LineWithLengthDto {
    LineDto lineDto;
    double length;

    @JsonCreator
    public LineWithLengthDto(@JsonProperty("name") String name,
                             @JsonProperty("startPoint") Point startPoint,
                             @JsonProperty("endPoint") Point endPoint,
                             @JsonProperty("length") double length) {
        this.lineDto = new LineDto(name, startPoint, endPoint);
        this.length = length;
    }
}
