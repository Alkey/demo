package com.example.demo.service.impl;

import com.example.demo.dto.ClientCreateDto;
import com.example.demo.entity.Client;
import com.example.demo.exception.PasswordMismatchException;
import com.example.demo.exception.UserAlreadyExistsException;
import com.example.demo.repository.ClientRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;

    @Override
    public Long addClient(ClientCreateDto dto) {
        if (!dto.getPassword().equals(dto.getRepeatPassword())) {
            throw new PasswordMismatchException("Password mismatch");
        }
        if (clientRepository.findClientByName(dto.getName()).isPresent()) {
            throw new UserAlreadyExistsException("User already exist");
        }
        Client client = new Client();
        client.setName(dto.getName());
        client.setRoles(Set.of(roleRepository.getOne(1L)));
        client.setPassword(encoder.encode(dto.getPassword()));
        return clientRepository.save(client).getId();
    }
}
