package com.example.demo.service;

import com.example.demo.dto.ClientCreateDto;
import com.example.demo.dto.RoleDto;

public interface ClientService {

    long add(ClientCreateDto dto);

    String setRole(RoleDto dto);
}
