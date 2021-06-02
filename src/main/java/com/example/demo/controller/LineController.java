package com.example.demo.controller;

import com.example.demo.dto.LineDto;
import com.example.demo.dto.LineWithLengthDto;
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
    public ResponseEntity<Void> add(@RequestBody LineDto dto) {
        return service.add(dto) ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
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
}
