package com.example.demo.controller;

import com.example.demo.dto.PlaceDto;
import com.example.demo.service.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/place")
public class PlaceController {
    private final PlaceService service;

    @PostMapping
    public ResponseEntity<Double> add(@RequestBody PlaceDto dto) {
        return ResponseEntity.ok(service.add(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlaceDto> get(@PathVariable long id) {
        return ResponseEntity.ok(service.get(id));
    }
}

