package com.example.demo.entity;

import lombok.Value;

@Value
public class Place {
    Long id;
    String name;
    String location;
    double length;
}
