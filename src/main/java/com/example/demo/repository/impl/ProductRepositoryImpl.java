package com.example.demo.repository.impl;

import com.example.demo.entity.Product;
import com.example.demo.jooq.sample.model.tables.Products;
import com.example.demo.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {
    private final DSLContext dsl;

    @Override
    public long add(Product product) {
        return Objects.requireNonNull(dsl.insertInto(Products.PRODUCTS, Products.PRODUCTS.NAME, Products.PRODUCTS.PRICE)
                .values(product.getName(), product.getPrice())
                .returningResult(Products.PRODUCTS.ID)
                .fetchOne())
                .into(long.class);
    }

    @Override
    public List<Product> getAll() {
        return dsl.selectFrom(Products.PRODUCTS)
                .fetchInto(Product.class);
    }

    @Override
    public int delete(long id) {
        return dsl.deleteFrom(Products.PRODUCTS)
                .where(Products.PRODUCTS.ID.eq(id))
                .execute();
    }
}
