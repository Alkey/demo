package com.example.demo.entity;

import lombok.Value;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Value
public class Product {
    Long id;
    @Size(min = 1) String name;
    @Positive
    double price;
}
