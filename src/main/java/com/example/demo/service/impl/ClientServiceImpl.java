package com.example.demo.service.impl;

import com.example.demo.dto.ClientCreateDto;
import com.example.demo.entity.Client;
import com.example.demo.entity.Role;
import com.example.demo.exception.ClientAlreadyExistsException;
import com.example.demo.exception.PasswordMismatchException;
import com.example.demo.repository.ClientRepository;
import com.example.demo.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final PasswordEncoder encoder;

    @Override
    public long add(ClientCreateDto dto) {
        if (dto.getPassword() == null || !dto.getPassword().equals(dto.getRepeatPassword())) {
            throw new PasswordMismatchException("Password mismatch");
        }
        if (dto.getName() == null || clientRepository.findClientByName(dto.getName()).isPresent()) {
            throw new ClientAlreadyExistsException("Client already exist");
        }
        Client client = new Client();
        client.setName(dto.getName());
        client.setRole(Role.USER);
        client.setPassword(encoder.encode(dto.getPassword()));
        return clientRepository.save(client).getId();
    }

    @Override
    @Transactional
    public boolean setRole(Long clientId, Role role) {
        return clientRepository.updateRole(role, clientId) > 0;
    }
}
