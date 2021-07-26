package com.example.demo.controller;

import com.example.demo.entity.Product;
import com.example.demo.service.ProductCheckService;
import com.example.demo.service.ProductService;
import com.example.demo.util.ProductCounter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final ProductCheckService productCheckService;

    @PostMapping
    public ResponseEntity<Long> add(@RequestBody
                                    @Valid Product product) {
        return ResponseEntity.ok(productService.add(product));
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAll() {
        return ResponseEntity.ok(productService.getAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable long id) {
        return productService.delete(id) ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> findById(@PathVariable long id) {
        return productService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    @PostMapping("/check")
    public ResponseEntity<Void> check(@RequestBody List<Long> productIds) {
        if (productCheckService.isComplete()) {
            try {
                productCheckService.checkProducts(productIds);
            } catch (InterruptedException | ExecutionException e) {
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/check")
    public ResponseEntity<ProductCounter> getCount() {
        return ResponseEntity.ok(productCheckService.getResult());
    }
}
