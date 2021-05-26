package com.example.demo.service.impl;

import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public long add(Product product) {
        return productRepository.add(product);
    }

    @Override
    public List<Product> getAll() {
        return productRepository.getAll();
    }

    @Override
    public boolean delete(long id) {
        return productRepository.delete(id) == 1;
    }
}
