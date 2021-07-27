package com.example.demo.service.impl;

import com.example.demo.service.ProductCheckService;
import com.example.demo.util.ProductCounter;
import com.example.demo.util.ProductValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
@RequiredArgsConstructor
public class ProductCheckServiceImpl implements ProductCheckService {
    private final ProductValidator validator;
    private final ConcurrentMap<Integer, ProductCounter> counters = new ConcurrentHashMap<>();

    @Override
    public int checkProducts(List<Long> productIds) {
        int counterId = productIds.hashCode();
        if (!counters.containsKey(counterId)) {
            counters.put(counterId, new ProductCounter(productIds.size()));
            productIds.forEach(productId -> validate(productId, counterId));
        }
        return counterId;
    }

    @Override
    public Optional<ProductCounter> findResult(int counterId) {
        return Optional.ofNullable(counters.get(counterId));
    }

    private void validate(long productId, int counterId) {
        validator.validateProduct(productId).thenAccept(valid -> checkIsValidProduct(valid, counterId));
    }

    private void checkIsValidProduct(boolean valid, int counterId) {
        ProductCounter counter = counters.get(counterId);
        counter.increment();
        if (valid) {
            counter.addValidProductsCount();
        }
    }
}
