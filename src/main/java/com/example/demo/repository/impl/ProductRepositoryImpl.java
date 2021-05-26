package com.example.demo.repository.impl;

import com.example.demo.entity.Product;
import com.example.demo.exception.EntityCreateException;
import com.example.demo.jooq.sample.model.tables.Products;
import com.example.demo.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {
    private final DSLContext dsl;

    @Override
    public long add(Product product) {
        return dsl.insertInto(Products.PRODUCTS)
                .set(dsl.newRecord(Products.PRODUCTS, product))
                .returning(Products.PRODUCTS.ID)
                .fetchOptional()
                .orElseThrow(() -> new EntityCreateException("Can't add product"))
                .getId();
    }

    @Override
    public List<Product> getAll() {
        return dsl.selectFrom(Products.PRODUCTS)
                .fetchInto(Product.class);
    }

    @Override
    public boolean delete(Long id) {
        return dsl.deleteFrom(Products.PRODUCTS)
                .where(Products.PRODUCTS.ID.eq(id))
                .execute() == 1;
    }
}
