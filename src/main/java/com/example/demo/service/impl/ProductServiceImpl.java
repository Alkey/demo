package com.example.demo.service.impl;

import com.example.demo.dto.ProductCreateDto;
import com.example.demo.dto.ProductDto;
import com.example.demo.entity.Product;
import com.example.demo.exception.EntityCreateException;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public long add(ProductCreateDto dto) {
        if (dto.getName() == null || dto.getName().isEmpty() || dto.getPrice() <= 0) {
            throw new EntityCreateException("Product name can't be null or empty, product price must be greater than 0");
        }
        Product product = new Product();
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        return productRepository.add(product);
    }

    @Override
    public List<ProductDto> getAll() {
        return productRepository.getAll().stream()
                .map(this::getDto)
                .collect(Collectors.toList());
    }

    @Override
    public boolean delete(long id) {
        return productRepository.delete(id);
    }

    private ProductDto getDto(Product product) {
        return new ProductDto(product.getId(), product.getName(), product.getPrice());
    }
}
