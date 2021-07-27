package com.example.demo.util;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

import static java.time.LocalDateTime.now;

@Component
public class ProductValidator {

    public CompletableFuture<Boolean> validateProduct(long id) {
        return CompletableFuture.supplyAsync(() -> {
            LocalDateTime past = now();
            while (now().isBefore(past.plusSeconds(3))) {
            }
            return id % 2 == 0;
        });
    }
}
