package com.example.demo.controller;

import com.example.demo.service.LinePolygonUtilsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LinePolygonUtilsController {
    private final LinePolygonUtilsService service;

    @GetMapping("/intersects")
    public ResponseEntity<Boolean> isIntersects(@RequestParam long lineId, @RequestParam long polygonId) {
        return ResponseEntity.ok(service.isIntersects(lineId, polygonId));
    }

    @GetMapping("/within")
    public ResponseEntity<Boolean> isWithIn(@RequestParam long lineId, @RequestParam long polygonId) {
        return ResponseEntity.ok(service.isWithIn(lineId, polygonId));
    }

    @GetMapping("/distance")
    public ResponseEntity<Double> getDistance(@RequestParam long lineId, @RequestParam long polygonId) {
        return ResponseEntity.ok(service.getDistance(lineId, polygonId));
    }
}
