package com.example.demo.controller;

import com.example.demo.dto.PlaceDto;
import com.example.demo.dto.PlaceWithLengthDto;
import com.example.demo.service.PlaceService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/place")
public class PlaceController {
    private final PlaceService service;

    @PostMapping
    public ResponseEntity<String> add(@RequestBody PlaceDto dto) {
        return service.add(dto) ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlaceWithLengthDto> get(@PathVariable long id) {
        try {
            Optional<PlaceWithLengthDto> place = service.findById(id);
            if (place.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.ok(place.get());
        } catch (JsonProcessingException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
