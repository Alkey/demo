package com.example.demo.dto;

import com.example.demo.entity.Point;
import lombok.Value;

@Value
public class PlaceDto {
    String name;
    Point startPoint;
    Point endPoint;

    @Override
    public String toString() {
        return "LINESTRING(" + startPoint.getLat() + " "
                + startPoint.getLon() + ", "
                + endPoint.getLat() + " "
                + endPoint.getLon() + ")";
    }
}
