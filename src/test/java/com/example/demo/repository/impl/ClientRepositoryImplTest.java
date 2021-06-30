package com.example.demo.repository.impl;

import com.example.demo.entity.Client;
import com.example.demo.entity.Role;
import org.jooq.DSLContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.example.demo.ConnectionUtils.getConnection;
import static com.example.demo.jooq.sample.model.tables.Client.CLIENT;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ClientRepositoryImplTest {
    private static final String NAME = "name";
    private static final String PASSWORD = "password";
    private static final DSLContext dsl = getConnection();
    private final ClientRepositoryImpl repository = new ClientRepositoryImpl(dsl);

    @Test
    public void addClientTest() {
        long id = repository.add(new Client(null, NAME, PASSWORD, Role.USER));
        Client client = dsl.select(CLIENT.ID, CLIENT.NAME, CLIENT.PASSWORD, CLIENT.ROLE)
                .from(CLIENT)
                .where(CLIENT.ID.eq(id))
                .fetchOneInto(Client.class);
        assertThat(client.getName(), is(NAME));
        assertThat(client.getPassword(), is(PASSWORD));
        assertThat(client.getRole(), is(Role.USER));
    }

    @Test
    public void setRoleTest() {
        long id = dsl.insertInto(CLIENT, CLIENT.NAME, CLIENT.PASSWORD, CLIENT.ROLE)
                .values(NAME, PASSWORD, Role.USER.name())
                .returningResult(CLIENT.ID)
                .fetchOne()
                .into(long.class);
        int addedRows = 1;
        assertThat(repository.setRole(id, Role.ADMIN), is(addedRows));
        Role role = dsl.select(CLIENT.ROLE)
                .from(CLIENT)
                .where(CLIENT.ID.eq(id))
                .fetchOneInto(Role.class);
        assertThat(role, is(Role.ADMIN));
    }

    @Test
    public void findByNameTest() {
        Client client = dsl.insertInto(CLIENT, CLIENT.NAME, CLIENT.PASSWORD, CLIENT.ROLE)
                .values(NAME, PASSWORD, Role.USER.name())
                .returningResult(CLIENT.ID, CLIENT.NAME, CLIENT.PASSWORD, CLIENT.ROLE)
                .fetchOne()
                .into(Client.class);
        assertThat(repository.findByName(NAME).get(), is(client));
    }

    @AfterEach
    public void clearDb() {
        dsl.deleteFrom(CLIENT).execute();
    }

    @BeforeAll
    public static void checkDb() {
        assertThat(dsl.selectCount().from(CLIENT).fetchOneInto(int.class), is(0));
    }
}
