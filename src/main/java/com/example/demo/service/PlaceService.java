package com.example.demo.service;

import com.example.demo.dto.PlaceDto;

public interface PlaceService {

    double add(PlaceDto dto);

    PlaceDto get(long id);
}
