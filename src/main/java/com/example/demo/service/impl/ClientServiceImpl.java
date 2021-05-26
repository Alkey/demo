package com.example.demo.service.impl;

import com.example.demo.dto.ClientCreateDto;
import com.example.demo.entity.Client;
import com.example.demo.entity.Role;
import com.example.demo.repository.ClientRepository;
import com.example.demo.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final PasswordEncoder encoder;

    @Override
    public long add(ClientCreateDto dto) {
        if (Objects.equals(dto.getPassword(), dto.getRepeatPassword()) && clientRepository.findByName(dto.getName()).isEmpty()) {
            Client client = new Client(null, dto.getName(), encoder.encode(dto.getPassword()), Role.USER);
            return clientRepository.add(client);
        }
        throw new IllegalArgumentException("Incorrect name or password");
    }

    @Override
    public boolean setRole(long clientId, Role role) {
        return clientRepository.setRole(clientId, role) == 1;
    }
}
