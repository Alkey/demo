package com.example.demo.controller;

import com.example.demo.dto.ClientCreateDto;
import com.example.demo.entity.Role;
import com.example.demo.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/clients")
public class ClientController {
    private final ClientService clientService;

    @PostMapping("/add")
    public long create(@RequestBody ClientCreateDto dto) {
        return clientService.add(dto);
    }

    @PutMapping("/set-role/{clientId}/{role}")
    public ResponseEntity<String> setRole(@PathVariable Long clientId,
                                          @PathVariable Role role) {
        if (clientService.setRole(clientId, role)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
}
