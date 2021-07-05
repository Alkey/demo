package com.example.demo.controller;

import com.example.demo.SecurityTestConfig;
import com.example.demo.dto.ClientCreateDto;
import com.example.demo.entity.Client;
import com.example.demo.entity.Role;
import com.example.demo.repository.ClientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = SecurityTestConfig.class)
public class ClientControllerTest {
    private static final String URL = "http://localhost:";
    @MockBean
    private ClientRepository repository;
    @SpyBean
    private PasswordEncoder encoder;
    @LocalServerPort
    private int port;

    private final TestRestTemplate restTemplate = new TestRestTemplate();

    @Test
    public void createClientTest() {
        String name = "name";
        String password = "password";
        long id = 1;
        ClientCreateDto dto = new ClientCreateDto(name, password, password);
        Client client = new Client(null, name, password, Role.USER);
        when(encoder.encode(password)).thenReturn(password);
        when(repository.findByName(name)).thenReturn(Optional.empty());
        when(repository.add(client)).thenReturn(id);
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(URL + port + "/clients/add", dto, Long.class);
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        assertThat(responseEntity.getBody(), is(id));
    }

    @Test
    public void setRoleBadRequestTest() {
        long clientId = 1;
        Role role = Role.ADMIN;
        String url = URL + port + "/clients/set-role/" + clientId + "/" + role.name();
        when(repository.setRole(clientId, role)).thenReturn(0);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, null, String.class);
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void setRoleTest() {
        long clientId = 1;
        Role role = Role.ADMIN;
        String url = URL + port + "/clients/set-role/" + clientId + "/" + role.name();
        when(repository.setRole(clientId, role)).thenReturn(1);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, null, String.class);
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
    }
}
