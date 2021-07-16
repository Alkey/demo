package com.example.demo.controller;

import com.example.demo.entity.FeatureCollection;
import com.example.demo.entity.GeoJsonPolygonGeometry;
import com.example.demo.service.FeatureCollectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/feature")
public class FeatureCollectionController {
    private final FeatureCollectionService service;

    @PostMapping
    public ResponseEntity<Long> add(@RequestBody GeoJsonPolygonGeometry geometry) {
        return ResponseEntity.ok(service.add(geometry));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FeatureCollection> get(@PathVariable long id) {
        try {
            return service.findById(id)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.badRequest().build());
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<FeatureCollection>> getAll() {
        try {
            return ResponseEntity.ok(service.getAll());
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
