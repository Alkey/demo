package com.example.demo.repository.impl;

import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static jooq.tables.Product.PRODUCT;


@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {
    private final DSLContext dsl;

    @Override
    public long add(Product product) {
        return Objects.requireNonNull(dsl.insertInto(PRODUCT, PRODUCT.NAME, PRODUCT.PRICE)
                .values(product.getName(), product.getPrice())
                .returningResult(PRODUCT.ID)
                .fetchOne())
                .into(long.class);
    }

    @Override
    public List<Product> getAll() {
        return dsl.selectFrom(PRODUCT)
                .fetchInto(Product.class);
    }

    @Override
    public int delete(long id) {
        return dsl.deleteFrom(PRODUCT)
                .where(PRODUCT.ID.eq(id))
                .execute();
    }

    @Override
    public Optional<Product> findById(long id) {
        return dsl.selectFrom(PRODUCT)
                .where(PRODUCT.ID.eq(id))
                .fetchOptionalInto(Product.class);
    }
}
