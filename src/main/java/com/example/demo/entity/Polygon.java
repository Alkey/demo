package com.example.demo.entity;

import lombok.Value;

@Value
public class Polygon {
    long id;
    String name;
    String geometry;
    double area;
}
