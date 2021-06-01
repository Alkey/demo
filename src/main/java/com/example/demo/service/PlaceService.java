package com.example.demo.service;

import com.example.demo.dto.PlaceDto;
import com.example.demo.dto.PlaceWithLengthDto;

public interface PlaceService {

    boolean add(PlaceDto dto);

    PlaceWithLengthDto get(long id);
}
