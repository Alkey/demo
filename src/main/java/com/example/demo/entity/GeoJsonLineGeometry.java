package com.example.demo.entity;

import com.example.demo.dto.LineDto;
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

    public LineDto toEntity() {
        List<Point> points = getPoints();
        return new LineDto("LineString", points.get(0), points.get(1));
    }

    private List<Point> getPoints() {
        return coordinates.stream()
                .map(coordinates -> new Point(coordinates.get(0), coordinates.get(1)))
                .collect(Collectors.toUnmodifiableList());
    }
}
