package com.example.demo.integration;

import com.example.demo.dto.ClientCreateDto;
import com.example.demo.entity.Client;
import com.example.demo.entity.Role;
import com.example.demo.repository.ClientRepository;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ClientControllerIntegrationTest {
    private static final String URL = "http://localhost:";
    @MockBean
    private ClientRepository repository;
    @SpyBean
    private PasswordEncoder encoder;
    @LocalServerPort
    private int port;
    @Value("#{environment.PASSWORD}")
    private String password;
    private final TestRestTemplate restTemplate = new TestRestTemplate(TestRestTemplate.HttpClientOption.ENABLE_COOKIES);

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

    @BeforeEach
    public void getJSessionId() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("username", "admin");
        map.add("password", password);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        String cookie = restTemplate.postForEntity(URL + port + "/login", request, String.class).getHeaders().get("Set-Cookie").get(0);
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultHeaders(List.of(new BasicHeader("Cookie", cookie))).build();
        restTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory(httpClient));
    }
}
