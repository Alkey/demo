package com.example.demo.repository;

import com.example.demo.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    long add(Product product);

    List<Product> getAll();

    int delete(long id);

    Optional<Product> findById(long id);
}
