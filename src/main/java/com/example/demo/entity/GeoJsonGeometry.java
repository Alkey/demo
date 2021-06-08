package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import static com.fasterxml.jackson.annotation.JsonSubTypes.Type;


@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
        property = "type")
@JsonSubTypes({
        @Type(value = LineGeometry.class, name = "LineString"),
        @Type(value = PolygonGeometry.class, name = "Polygon")
})
public interface GeoJsonGeometry {

    @JsonProperty("type")
    String getType();
}
