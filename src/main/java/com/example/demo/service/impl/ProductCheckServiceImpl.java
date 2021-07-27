package com.example.demo.service.impl;

import com.example.demo.util.ProductValidator;
import com.example.demo.service.ProductCheckService;
import com.example.demo.util.ProductCounter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductCheckServiceImpl implements ProductCheckService {
    private final ProductValidator validator;
    private ProductCounter counter;

    @Override
    public boolean checkProducts(List<Long> productIds) {
        if (isProcessOver()) {
            counter = new ProductCounter(productIds.size());
            productIds.forEach(this::validate);
            return true;
        }
        return false;
    }

    @Override
    public ProductCounter getResult() {
        return counter;
    }

    private boolean isProcessOver() {
        return counter == null || counter.getCount() == counter.getTotal();
    }

    private void validate(long id) {
        validator.validateProduct(id).thenAccept(this::checkIsValidProduct);
    }

    private void checkIsValidProduct(boolean isTrue) {
        counter.increment();
        if (isTrue) {
            counter.addValidProductsCount();
        }
    }
}
