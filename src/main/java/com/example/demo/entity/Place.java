package com.example.demo.entity;

import lombok.Data;

@Data
public class Place {
    private Long id;
    private String name;
    private String location;
    private double length;
}
