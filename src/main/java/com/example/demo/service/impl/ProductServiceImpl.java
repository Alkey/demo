package com.example.demo.service.impl;

import com.example.demo.dto.ProductCreateDto;
import com.example.demo.dto.ProductDto;
import com.example.demo.entity.Product;
import com.example.demo.exception.ProductCreateException;
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
        if (dto.getName() == null || dto.getName().length() == 0 || dto.getPrice() <= 0) {
            throw new ProductCreateException("Product name can't be null or empty, product price must be greater than 0");
        }
        Product product = new Product();
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        return productRepository.save(product).getId();
    }

    @Override
    public List<ProductDto> getAll() {
        return productRepository.findAll().stream()
                .map(this::getDto)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(long id) {
        productRepository.deleteById(id);
    }

    private ProductDto getDto(Product product) {
        return new ProductDto(product.getId(), product.getName(), product.getPrice());
    }
}
