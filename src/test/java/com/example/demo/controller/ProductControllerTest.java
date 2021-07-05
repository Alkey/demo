package com.example.demo.controller;

import com.example.demo.SecurityTestConfig;
import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = SecurityTestConfig.class)
public class ProductControllerTest {
    private static final String URL = "http://localhost:";
    @MockBean
    private ProductRepository repository;
    @LocalServerPort
    private int port;

    private final TestRestTemplate restTemplate = new TestRestTemplate();

    @Test
    public void addProductTest() {
        long id = 1;
        Product product = new Product();
        product.setName("name");
        product.setPrice(25.33);
        when(repository.add(product)).thenReturn(id);
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(URL + port + "products", product, Long.class);
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        assertThat(responseEntity.getBody(), is(id));
    }

    @Test
    public void getAllProductsTest() {
        when(repository.getAll()).thenReturn(Collections.emptyList());
        ResponseEntity<List> responseEntity = restTemplate.getForEntity(URL + port + "products", List.class);
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        assertThat(responseEntity.getBody().isEmpty(), is(true));
    }

    @Test
    public void deleteProductTest() {
        long id = 1;
        String url = URL + port + "products/" + id;
        when(repository.delete(id)).thenReturn(1);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.DELETE, null, String.class);
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void deleteProductBadRequestTest() {
        long id = 1;
        String url = URL + port + "products/" + id;
        when(repository.delete(id)).thenReturn(0);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.DELETE, null, String.class);
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }
}
