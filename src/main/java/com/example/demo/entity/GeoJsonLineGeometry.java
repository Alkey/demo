package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;

@Value
public class GeoJsonLineGeometry implements GeoJsonGeometry {
    String type = "LineString";
    @Size(min = 2)
    @NotNull
    List<@Size(min = 2) @NotNull List<@NotNull Double>> coordinates;

    @JsonCreator
    public GeoJsonLineGeometry(@JsonProperty("coordinates") List<List<Double>> coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public String toWKTString() {
        return coordinates.stream()
                .map(coordinates -> coordinates.get(0) + " " + coordinates.get(1))
                .collect(Collectors.joining(",", "LINESTRING(", ")"));
    }
}
