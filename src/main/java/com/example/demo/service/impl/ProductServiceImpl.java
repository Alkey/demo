package com.example.demo.service.impl;

import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.example.demo.config.RedisConfig.ALL_PRODUCTS;
import static com.example.demo.config.RedisConfig.PRODUCTS;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @CacheEvict(value = ALL_PRODUCTS, allEntries = true)
    @Override
    public long add(Product product) {
        return productRepository.add(product);
    }

    @Cacheable(ALL_PRODUCTS)
    @Override
    public List<Product> getAll() {
        return productRepository.getAll();
    }

    @Caching(evict = {
            @CacheEvict(value = ALL_PRODUCTS, allEntries = true),
            @CacheEvict(value = PRODUCTS, key = "#id")
    })
    @Override
    public boolean delete(long id) {
        return productRepository.delete(id) == 1;
    }

    @Cacheable(cacheNames = PRODUCTS, key = "#id")
    @Override
    public Optional<Product> findById(long id) {
        return productRepository.findById(id);
    }
}
