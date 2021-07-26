package com.example.demo.service.impl;

import com.example.demo.service.ProductCheckService;
import com.example.demo.util.ProductCounter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static java.time.LocalDateTime.now;

@Service
@RequiredArgsConstructor
public class ProductCheckServiceImpl implements ProductCheckService {
    private final ProductCounter counter;

    @Override
    public void checkProducts(List<Long> productIds) {
        resetCounter(productIds.size());
        productIds.forEach(this::check);
    }

    @Override
    public boolean isComplete() {
        return counter.getCount() == counter.getQuantity();
    }

    @Override
    public ProductCounter getResult() {
        return counter;
    }

    private void resetCounter(int quantity) {
        counter.setCount(0);
        counter.getResult().clear();
        counter.setQuantity(quantity);
    }

    private void check(long id) {
        CompletableFuture.runAsync(() -> {
            LocalDateTime past = LocalDateTime.now();
            while (now().isBefore(past.plusSeconds(3))) {
            }
            counter.increment();
            counter.setResult(id % 2 == 0);
        });
    }
}
