package com.example.demo.repository.impl;

import com.example.demo.entity.Client;
import com.example.demo.entity.Role;
import com.example.demo.exception.EntityCreateException;
import com.example.demo.jooq.sample.model.tables.Clients;
import com.example.demo.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ClientRepositoryImpl implements ClientRepository {
    private final DSLContext dsl;

    @Override
    public long add(Client client) {
        return dsl.insertInto(Clients.CLIENTS)
                .set(dsl.newRecord(Clients.CLIENTS, client))
                .returning(Clients.CLIENTS.ID)
                .fetchOptional()
                .orElseThrow(() -> new EntityCreateException("Can't save client"))
                .getId();
    }

    @Override
    public boolean setRole(Long clientId, Role role) {
        return dsl.update(Clients.CLIENTS)
                .set(Clients.CLIENTS.ROLE, role.name())
                .where(Clients.CLIENTS.ID.eq(clientId))
                .execute() == 1;
    }

    @Override
    public Optional<Client> getByName(String name) {
        return dsl.selectFrom(Clients.CLIENTS)
                .where(Clients.CLIENTS.NAME.eq(name))
                .fetchOptionalInto(Client.class);
    }
}
