package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;

@Value
public class GeoJsonPolygonGeometry implements GeoJsonGeometry {
    String type = "Polygon";
    @Size(min = 1)
    @NotNull
    List<@Size(min = 4) @NotNull List<@Size(min = 2) @NotNull List<@NotNull Double>>> coordinates;

    @JsonCreator
    public GeoJsonPolygonGeometry(@JsonProperty("coordinates") List<List<List<Double>>> coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public String toWKTString() {
        return coordinates.stream()
                .map(this::getRing)
                .collect(Collectors.joining(",", "POLYGON(", ")"));
    }

    private String getRing(List<List<Double>> points) {
        return points.stream()
                .map(coordinates -> coordinates.get(0) + " " + coordinates.get(1))
                .collect(Collectors.joining(",", "(", ")"));
    }
}
