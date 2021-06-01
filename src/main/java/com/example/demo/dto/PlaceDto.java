package com.example.demo.dto;

import com.example.demo.entity.Point;
import lombok.Value;

@Value
public class PlaceDto {
    String name;
    Point startPoint;
    Point endPoint;
    double length;

    @Override
    public String toString() {
        return "LINESTRING(" + startPoint.getLatitude() + " "
                + startPoint.getLongitude() + ", "
                + endPoint.getLatitude() + " "
                + endPoint.getLongitude() + ")";
    }
}
