package com.example.demo.service;

import com.example.demo.util.ProductCounter;

import java.util.List;
import java.util.Optional;

public interface ProductCheckService {
    int checkProducts(List<Long> productIds);

    Optional<ProductCounter> findResult(int id);
}
