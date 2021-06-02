package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class Point {
    double lat;
    double lon;

    @JsonCreator
    public Point(
            @JsonProperty("lat") double lat,
            @JsonProperty("lon") double lon) {
        this.lat = lat;
        this.lon = lon;
    }
}
