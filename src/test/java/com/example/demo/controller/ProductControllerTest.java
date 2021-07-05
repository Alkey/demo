package com.example.demo.controller;

import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductControllerTest {
    private static final String URL = "http://localhost:";
    @MockBean
    private ProductRepository repository;
    @LocalServerPort
    private int port;
    @Value("#{environment.PASSWORD}")
    private String password;

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

    @BeforeEach
    public void getJSessionId() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("username", "admin");
        map.add("password", password);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        String cookie = restTemplate.postForEntity(URL + port + "/login", request, String.class).getHeaders().get("Set-Cookie").get(0);
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultHeaders(List.of(new BasicHeader("Cookie", cookie))).build();
        restTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory(httpClient));
    }
}
