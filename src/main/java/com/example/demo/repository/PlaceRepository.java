package com.example.demo.repository;

import com.example.demo.entity.Place;

public interface PlaceRepository {

    double add(Place place);

    Place get(long id);
}
