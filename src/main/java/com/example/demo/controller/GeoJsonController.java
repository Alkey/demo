package com.example.demo.controller;

import com.example.demo.entity.GeoJsonGeometry;
import com.example.demo.service.GeometryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/geometry")
public class GeoJsonController {
    private final GeometryService service;

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Valid GeoJsonGeometry geometry) {
        if (service.add(geometry)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
}
