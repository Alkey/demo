package com.example.demo.controller;

import com.example.demo.dto.PolygonWithAreaDto;
import com.example.demo.entity.GeoJsonGeometry;
import com.example.demo.entity.GeoJsonPolygonGeometry;
import com.example.demo.service.PolygonService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/polygon")
public class PolygonController {
    private final PolygonService service;

    @PostMapping
    public ResponseEntity<Void> add(@RequestBody
                                    @Valid GeoJsonPolygonGeometry geometry) {
        return service.add(geometry) ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
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

    @GetMapping("/intersection")
    public ResponseEntity<GeoJsonGeometry> getPolygonIntersection(@RequestParam long firstId, @RequestParam long secondId) {
        try {
            return ResponseEntity.ok(service.getPolygonIntersection(firstId, secondId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
