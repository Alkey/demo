package com.example.demo.service;

import com.example.demo.dto.PlaceDto;
import com.example.demo.dto.PlaceWithLengthDto;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Optional;

public interface PlaceService {

    boolean add(PlaceDto dto);

    Optional<PlaceWithLengthDto> findById(long id) throws JsonProcessingException;
}
