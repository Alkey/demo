package com.example.demo.repository;

import com.example.demo.entity.Client;
import com.example.demo.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findClientByName(String name);

    @Modifying
    @Query("UPDATE Client c set c.role = :role WHERE c.id = :clientId")
    int updateRole(@Param("role") Role role, @Param("clientId") Long clientId);
}
