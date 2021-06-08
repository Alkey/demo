package com.example.demo.entity;

import com.example.demo.dto.LineDto;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Value;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;

@Value
@JsonTypeName("LineString")
public class LineGeometry implements GeoJsonGeometry {
    @Size(min = 2)
    @NotNull
    List<@Size(min = 2) @NotNull List<@NotNull Double>> coordinates;

    @JsonCreator
    public LineGeometry(@JsonProperty("coordinates") List<List<Double>> coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public String getType() {
        return "LineString";
    }

    public LineDto toEntity() {
        List<Point> points = getPoints();
        return new LineDto("LineString", points.get(0), points.get(1));
    }

    private List<Point> getPoints() {
        return coordinates.stream()
                .map(c -> new Point(c.get(0), c.get(1)))
                .collect(Collectors.toUnmodifiableList());
    }
}
