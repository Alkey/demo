package com.example.demo.controller;

import com.example.demo.entity.Product;
import com.example.demo.service.ProductCheckService;
import com.example.demo.service.ProductService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.example.demo.util.ObjectMapperUtil.getMapper;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ProductControllerUnitTest {
    private static final String URL = "/products";
    private static final long ID = 1;
    private final ProductService service = mock(ProductService.class);
    private final ProductCheckService checkService = mock(ProductCheckService.class);
    private final ProductController controller = new ProductController(service, checkService);
    private final MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    private final ObjectMapper mapper = getMapper();

    @Test
    public void addProductTest() throws Exception {
        Product product = createProduct("name", 25.2);
        when(service.add(product)).thenReturn(ID);
        long result = Long.parseLong(mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(product)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString());
        assertThat(result, is(ID));
    }

    @Test
    public void getAllProductsTest() throws Exception {
        List<Product> products = List.of(createProduct("name", 2.2), createProduct("name2", 3.3));
        when(service.getAll()).thenReturn(products);
        String content = mockMvc.perform(get(URL))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        List<Product> result = mapper.readValue(content, new TypeReference<>() {
        });
        assertThat(result, is(products));
    }

    @Test
    public void deleteProductTest() throws Exception {
        when(service.delete(ID)).thenReturn(true);
        mockMvc.perform(delete(URL + "/{id}", ID))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteNotExistProductTest() throws Exception {
        when(service.delete(ID)).thenReturn(false);
        mockMvc.perform(delete(URL + "/{id}", ID))
                .andExpect(status().isBadRequest());
    }

    private Product createProduct(String name, double price) {
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        return product;
    }
}
