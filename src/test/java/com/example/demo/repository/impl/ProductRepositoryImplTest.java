package com.example.demo.repository.impl;

import com.example.demo.entity.Product;
import jooq.tables.records.ProductRecord;
import org.jooq.DSLContext;
import org.jooq.InsertValuesStep2;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.example.demo.ConnectionUtils.getConnection;
import static jooq.tables.Product.PRODUCT;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ProductRepositoryImplTest {
    private static final String NAME = "name";
    private static final double PRICE = 25.00;
    private static final DSLContext dsl = getConnection();
    private final ProductRepositoryImpl repository = new ProductRepositoryImpl(dsl);

    @Test
    public void addProductTest() {
        long id = repository.add(getProduct(NAME, PRICE));
        Product product = dsl.select(PRODUCT.NAME, PRODUCT.PRICE)
                .from(PRODUCT)
                .where(PRODUCT.ID.eq(id))
                .fetchOneInto(Product.class);
        assertThat(product.getName(), is(NAME));
        assertThat(product.getPrice(), is(PRICE));
    }

    @Test
    public void getAllProductsTest() {
        InsertValuesStep2<ProductRecord, String, Double> insertValuesSteps = dsl.insertInto(PRODUCT, PRODUCT.NAME, PRODUCT.PRICE);
        List.of(getProduct(NAME, PRICE), getProduct("Banana", 122.33))
                .forEach(product -> insertValuesSteps.values(product.getName(), product.getPrice()));
        insertValuesSteps.execute();
        assertThat(repository.getAll().size(), is(2));
    }

    @Test
    public void deleteProductTest() {
        int removedRows = 1;
        long id = dsl.insertInto(PRODUCT, PRODUCT.NAME, PRODUCT.PRICE)
                .values(NAME, PRICE)
                .returningResult(PRODUCT.ID)
                .fetchOne()
                .into(long.class);
        assertThat(repository.delete(id), is(removedRows));
    }

    @AfterEach
    public void clearDb() {
        dsl.deleteFrom(PRODUCT).execute();
    }

    @BeforeAll
    public static void checkDb() {
        assertThat(dsl.selectCount().from(PRODUCT).fetchOneInto(int.class), is(0));

    }

    private Product getProduct(String name, double price) {
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        return product;
    }
}
