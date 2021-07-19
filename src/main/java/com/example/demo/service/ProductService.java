package com.example.demo.service;

import com.example.demo.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    long add(Product product);

    List<Product> getAll();

    boolean delete(long id);

    Optional<Product> findById(long id);
}
