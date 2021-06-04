package com.example.demo.entity;

import lombok.Value;

@Value
public class Line {
    Long id;
    String name;
    String geometry;
    double length;
}
