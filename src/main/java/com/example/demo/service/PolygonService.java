package com.example.demo.service;

import com.example.demo.dto.PolygonWithAreaDto;
import com.example.demo.entity.GeoJsonGeometry;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;
import java.util.Optional;

public interface PolygonService {

    boolean add(GeoJsonGeometry geometry);

    Optional<PolygonWithAreaDto> findById(long id) throws JsonProcessingException;

    GeoJsonGeometry getPolygonIntersection(long firstPolygonId, long secondPolygonId) throws JsonProcessingException;

    List<GeoJsonGeometry> getContainedInPolygonGeometries(String polygon);
}
