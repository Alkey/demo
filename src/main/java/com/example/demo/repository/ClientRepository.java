package com.example.demo.repository;

import com.example.demo.entity.Client;
import com.example.demo.entity.Role;

import java.util.Optional;

public interface ClientRepository {
    long add(Client client);

    int setRole(long clientId, Role role);

    Optional<Client> findByName(String name);
}
