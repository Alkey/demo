package com.example.demo.service;

import com.example.demo.util.ProductCounter;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface ProductCheckService {
    void checkProducts(List<Long> productIds) throws InterruptedException, ExecutionException;

    boolean isComplete();

    ProductCounter getResult();
}
