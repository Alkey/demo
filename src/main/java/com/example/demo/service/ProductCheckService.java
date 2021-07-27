package com.example.demo.service;

import com.example.demo.util.ProductCounter;

import java.util.List;

public interface ProductCheckService {
    boolean checkProducts(List<Long> productIds);

    ProductCounter getResult();
}
