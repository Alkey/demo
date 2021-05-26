package com.example.demo.service;

import com.example.demo.dto.ProductCreateDto;
import com.example.demo.dto.ProductDto;

import java.util.List;

public interface ProductService {
    long add(ProductCreateDto dto);

    List<ProductDto> getAll();

    boolean delete(long id);
}
