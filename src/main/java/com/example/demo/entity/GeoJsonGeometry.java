package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.io.Serializable;

import static com.fasterxml.jackson.annotation.JsonSubTypes.Type;


@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
        property = "type")
@JsonSubTypes({
        @Type(value = GeoJsonLineGeometry.class, name = "LineString"),
        @Type(value = GeoJsonPolygonGeometry.class, name = "Polygon")
})
public interface GeoJsonGeometry extends Serializable {

    String getType();

    String toWKTString();
}
