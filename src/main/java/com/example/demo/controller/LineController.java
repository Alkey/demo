package com.example.demo.controller;

import com.example.demo.dto.LineWithLengthDto;
import com.example.demo.entity.GeoJsonLineGeometry;
import com.example.demo.entity.Point;
import com.example.demo.service.LineService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/line")
public class LineController {
    private final LineService service;

    @PostMapping
    public ResponseEntity<Void> add(@RequestBody GeoJsonLineGeometry geometry) {
        return service.add(geometry) ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LineWithLengthDto> get(@PathVariable long id) {
        try {
            return service.findById(id)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.badRequest().build());
        } catch (JsonProcessingException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/intersection")
    public ResponseEntity<Point> getLineStringIntersection(@RequestParam long firstId, @RequestParam long secondId) {
        try {
            return service.getLineStringIntersection(firstId, secondId)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.badRequest().build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
