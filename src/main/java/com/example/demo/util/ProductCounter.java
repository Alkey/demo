package com.example.demo.util;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Data
public class ProductCounter {
    private int count;
    private int quantity;
    private List<Boolean> result = new ArrayList<>();

    public void increment() {
        synchronized (this) {
            count += 1;
        }
    }

    public void setResult(boolean isValid) {
        synchronized (this) {
            result.add(isValid);
        }
    }
}
