package com.example.demo.repository;

import com.example.demo.entity.Line;

import java.util.Optional;

public interface LineRepository {

    int add(String name, String location);

    Optional<Line> findById(long id);

    String getLineStringIntersection(long firstLineId, long secondLineId);
}
