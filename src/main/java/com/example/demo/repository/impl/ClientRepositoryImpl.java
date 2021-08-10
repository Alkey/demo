package com.example.demo.repository.impl;

import com.example.demo.entity.Client;
import com.example.demo.entity.Role;
import com.example.demo.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.Optional;

import static jooq.Tables.CLIENT;


@Repository
@RequiredArgsConstructor
public class ClientRepositoryImpl implements ClientRepository {
    private final DSLContext dsl;

    @Override
    public long add(Client client) {
        return Objects.requireNonNull(dsl.insertInto(CLIENT, CLIENT.NAME, CLIENT.PASSWORD, CLIENT.ROLE)
                .values(client.getName(), client.getPassword(), client.getRole().name())
                .returningResult(CLIENT.ID)
                .fetchOne())
                .into(long.class);
    }

    @Override
    public int setRole(long clientId, Role role) {
        return dsl.update(CLIENT)
                .set(CLIENT.ROLE, role.name())
                .where(CLIENT.ID.eq(clientId))
                .execute();
    }

    @Override
    public Optional<Client> findByName(String name) {
        return dsl.selectFrom(CLIENT)
                .where(CLIENT.NAME.eq(name))
                .fetchOptionalInto(Client.class);
    }
}
