package com.example.demo.service;

import com.example.demo.dto.PlaceDto;
import com.example.demo.dto.PlaceWithLengthDto;

import java.util.Optional;

public interface PlaceService {

    boolean add(PlaceDto dto);

    Optional<PlaceWithLengthDto> findById(long id);
}
