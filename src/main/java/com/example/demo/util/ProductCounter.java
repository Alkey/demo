package com.example.demo.util;

import lombok.Getter;

@Getter
public class ProductCounter {
    private int count;
    private final int total;
    private int validProductsCount;

    public ProductCounter(int total) {
        this.total = total;
    }

    public synchronized void increment() {
        count++;
    }

    public synchronized void addValidProductsCount() {
        validProductsCount++;
    }
}
