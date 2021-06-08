package com.example.demo.entity;

import com.example.demo.dto.PolygonDto;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Value;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;

@Value
@JsonTypeName("Polygon")
public class PolygonGeometry implements GeoJsonGeometry {
    @Size(min = 1)
    @NotNull
    List<@Size(min = 4) @NotNull List<@Size(min = 2) @NotNull List<@NotNull Double>>> coordinates;

    @JsonCreator
    public PolygonGeometry(@JsonProperty("coordinates") List<List<List<Double>>> coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public String getType() {
        return "Polygon";
    }

    public PolygonDto toEntity() {
        return new PolygonDto("Polygon", getPolygonPoints());
    }

    private List<List<Point>> getPolygonPoints() {
        return coordinates.stream()
                .map(this::getPoints)
                .collect(Collectors.toUnmodifiableList());
    }

    private List<Point> getPoints(List<List<Double>> points) {
        return points.stream()
                .map(coordinates -> new Point(coordinates.get(0), coordinates.get(1)))
                .collect(Collectors.toUnmodifiableList());
    }
}
