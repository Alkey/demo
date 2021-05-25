package com.example.demo.exception;

public class ProductCreateException extends RuntimeException {
    public ProductCreateException(String message) {
        super(message);
    }
}
