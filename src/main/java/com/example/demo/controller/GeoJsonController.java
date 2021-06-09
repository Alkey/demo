package com.example.demo.controller;

import com.example.demo.entity.GeoJsonGeometry;
import com.example.demo.entity.Point;
import com.example.demo.service.GeoJsonGeometryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/geojson")
public class GeoJsonController {
    private final GeoJsonGeometryService service;

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Valid GeoJsonGeometry geometry) {
        return service.add(geometry) ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    @GetMapping("/intersects/{lineId}/{polygonId}")
    public ResponseEntity<Boolean> isIntersects(@PathVariable long lineId, @PathVariable long polygonId) {
        return service.isIntersects(lineId, polygonId).map(ResponseEntity::ok).orElse(ResponseEntity.badRequest().build());
    }

    @GetMapping("/within/{lineId}/{polygonId}")
    public ResponseEntity<Boolean> isWithIn(@PathVariable long lineId, @PathVariable long polygonId) {
        return service.isWithIn(lineId, polygonId).map(ResponseEntity::ok).orElse(ResponseEntity.badRequest().build());
    }

    @GetMapping("/distance/{lineId}/{polygonId}")
    public ResponseEntity<Double> getDistance(@PathVariable long lineId, @PathVariable long polygonId) {
        return service.getDistance(lineId, polygonId).map(ResponseEntity::ok).orElse(ResponseEntity.badRequest().build());
    }

    @GetMapping("/polygon/intersection/{firstId}/{secondId}")
    public ResponseEntity<GeoJsonGeometry> getPolygonIntersection(@PathVariable long firstId, @PathVariable long secondId) {
        try {
            return ResponseEntity.ok(service.getPolygonIntersection(firstId, secondId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/line/intersection/{firstId}/{secondId}")
    public ResponseEntity<Point> getLineStringIntersection(@PathVariable long firstId, @PathVariable long secondId) {
        try {
            return service.getLineStringIntersection(firstId, secondId)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.badRequest().build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
