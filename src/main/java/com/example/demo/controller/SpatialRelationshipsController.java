package com.example.demo.controller;

import com.example.demo.service.SpatialRelationshipsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SpatialRelationshipsController {
    private final SpatialRelationshipsService service;

    @GetMapping("/intersects")
    public ResponseEntity<Boolean> isIntersects(@RequestParam long lineId, @RequestParam long polygonId) {
        return service.isIntersects(lineId, polygonId).map(ResponseEntity::ok).orElse(ResponseEntity.badRequest().build());
    }

    @GetMapping("/within")
    public ResponseEntity<Boolean> isWithIn(@RequestParam long lineId, @RequestParam long polygonId) {
        return service.isWithIn(lineId, polygonId).map(ResponseEntity::ok).orElse(ResponseEntity.badRequest().build());
    }

    @GetMapping("/distance")
    public ResponseEntity<Double> getDistance(@RequestParam long lineId, @RequestParam long polygonId) {
        return service.getDistance(lineId, polygonId).map(ResponseEntity::ok).orElse(ResponseEntity.badRequest().build());
    }
}
