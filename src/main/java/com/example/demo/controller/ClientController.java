package com.example.demo.controller;

import com.example.demo.dto.ClientCreateDto;
import com.example.demo.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class ClientController {
    private final ClientService clientService;

    @PostMapping("/add")
    public Long createUser(@RequestBody ClientCreateDto dto) {
        return clientService.addClient(dto);
    }
}
