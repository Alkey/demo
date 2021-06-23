package com.example.demo.controller;

import com.example.demo.entity.GeoJsonGeometry;
import com.example.demo.service.GeoJsonGeometryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/geojson")
public class GeoJsonController {
    private final GeoJsonGeometryService geometryService;

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Valid GeoJsonGeometry geometry) {
        return geometryService.add(geometry) ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    @PostMapping("/restore")
    public ResponseEntity<Void> restore() {
        return geometryService.restore() ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }
}
