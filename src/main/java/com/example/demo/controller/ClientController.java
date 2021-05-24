package com.example.demo.controller;

import com.example.demo.dto.ClientCreateDto;
import com.example.demo.dto.RoleDto;
import com.example.demo.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("clients")
public class ClientController {
    private final ClientService clientService;

    @PostMapping("/add")
    public long create(@RequestBody ClientCreateDto dto) {
        return clientService.add(dto);
    }

    @PostMapping("/role")
    public String setRole(@RequestBody RoleDto dto) {
        return clientService.setRole(dto);
    }
}
