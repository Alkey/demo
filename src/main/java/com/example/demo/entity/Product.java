package com.example.demo.entity;

import lombok.Data;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Data
public class Product {
    private Long id;
    @Size(min = 1)
    private String name;
    @Positive
    private double price;
}
