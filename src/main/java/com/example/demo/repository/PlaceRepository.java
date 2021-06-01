package com.example.demo.repository;

import com.example.demo.entity.Place;

import java.util.Optional;

public interface PlaceRepository {

    int add(Place place);

    Optional<Place> findById(long id);
}
