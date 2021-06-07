package com.example.demo.service;

import com.example.demo.dto.PolygonDto;
import com.example.demo.dto.PolygonWithAreaDto;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Optional;

public interface PolygonService {

    boolean add(PolygonDto dto);

    Optional<PolygonWithAreaDto> findById(long id) throws JsonProcessingException;
}
