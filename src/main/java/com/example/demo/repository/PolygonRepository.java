package com.example.demo.repository;

import com.example.demo.entity.Polygon;

import java.util.Optional;

public interface PolygonRepository {

    int add(String name, String polygon);

    Optional<Polygon> findById(long id);
}
