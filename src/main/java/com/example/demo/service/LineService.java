package com.example.demo.service;

import com.example.demo.dto.LineDto;
import com.example.demo.dto.LineWithLengthDto;
import com.example.demo.entity.GeoJsonGeometry;
import com.example.demo.entity.Point;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;
import java.util.Optional;

public interface LineService {

    boolean add(LineDto dto);

    Optional<LineWithLengthDto> findById(long id) throws JsonProcessingException;

    Optional<Point> getLineStringIntersection(long firstLineId, long secondLineId) throws JsonProcessingException;

    List<GeoJsonGeometry> getContainedInPolygonLines(String polygon);
}
