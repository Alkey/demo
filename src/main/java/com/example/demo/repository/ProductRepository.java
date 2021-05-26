package com.example.demo.repository;

import com.example.demo.entity.Product;

import java.util.List;

public interface ProductRepository {
    long add(Product product);

    List<Product> getAll();

    boolean delete(Long id);
}
