package com.example.demo.controller;

import com.example.demo.entity.FeatureCollection;
import com.example.demo.entity.GeoJsonPolygonGeometry;
import com.example.demo.service.FeatureCollectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/feature")
public class FeatureCollectionController {
    private final FeatureCollectionService service;

    @PostMapping("/add")
    public ResponseEntity<Void> add(@RequestBody GeoJsonPolygonGeometry geometry) {
        return service.add(geometry) ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FeatureCollection> get(@PathVariable long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    @GetMapping("/all")
    public ResponseEntity<List<FeatureCollection>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
