package com.example.demo.service;

import com.example.demo.dto.ClientCreateDto;
import com.example.demo.entity.Role;

public interface ClientService {

    long add(ClientCreateDto dto);

    boolean setRole(Long clientId, Role role);
}
