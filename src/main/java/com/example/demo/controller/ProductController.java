package com.example.demo.controller;

import com.example.demo.dto.ProductCreateDto;
import com.example.demo.dto.ProductDto;
import com.example.demo.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    @PostMapping("/add")
    public long add(@RequestBody ProductCreateDto dto) {
        return productService.add(dto);
    }

    @GetMapping("/all")
    public List<ProductDto> getAll() {
        return productService.getAll();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable long id) {
        if (productService.delete(id)) {
            return ResponseEntity.ok().build();

        }
        return ResponseEntity.badRequest().build();
    }
}
