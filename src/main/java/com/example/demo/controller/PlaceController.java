package com.example.demo.controller;

import com.example.demo.dto.PlaceDto;
import com.example.demo.dto.PlaceWithLengthDto;
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
    public ResponseEntity<String> add(@RequestBody PlaceDto dto) {
        if (service.add(dto)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlaceWithLengthDto> get(@PathVariable long id) {
        return ResponseEntity.ok(service.get(id));
    }
}

