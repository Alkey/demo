package com.example.demo.controller;

import com.example.demo.dto.PolygonDto;
import com.example.demo.dto.PolygonWithAreaDto;
import com.example.demo.service.PolygonService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/polygon")
public class PolygonController {
    private final PolygonService service;

    @PostMapping
    public ResponseEntity<Void> add(@RequestBody PolygonDto dto) {
        return service.add(dto) ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PolygonWithAreaDto> get(@PathVariable long id) {
        try {
            return service.findById(id)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.badRequest().build());
        } catch (JsonProcessingException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
