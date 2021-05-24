package com.example.demo.service.impl;

import com.example.demo.dto.ClientCreateDto;
import com.example.demo.dto.RoleDto;
import com.example.demo.entity.Client;
import com.example.demo.entity.Role;
import com.example.demo.exception.ClientAlreadyExistsException;
import com.example.demo.exception.ClientNotFoundException;
import com.example.demo.exception.PasswordMismatchException;
import com.example.demo.exception.RoleParseException;
import com.example.demo.repository.ClientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


public class ClientServiceImplTest {
    private static final String CLIENT_NAME = "User";
    private static final String PASSWORD = "123";
    private static final String ENCODED_PASSWORD = "encoded_password";
    private static final Long ID = 1L;
    private static final String INCORRECT_ROLE = "incorrect_role";
    private static final String USER_ROLE = "user";
    private final ClientRepository clientRepository = mock(ClientRepository.class);
    private final PasswordEncoder encoder = mock(PasswordEncoder.class);
    private final ClientServiceImpl service = new ClientServiceImpl(clientRepository, encoder);

    @Test
    public void shouldThrowPasswordMismatchExceptionWhenTryAddClient() {
        ClientCreateDto dto = ClientCreateDto.builder()
                .name(CLIENT_NAME)
                .password(PASSWORD)
                .repeatPassword("1234")
                .build();

        assertThrows(PasswordMismatchException.class, () -> service.add(dto));
    }

    @Test
    public void shouldThrowUserAlreadyExistExceptionWhenTryAddClient() {
        ClientCreateDto dto = ClientCreateDto.builder()
                .name(CLIENT_NAME)
                .password(PASSWORD)
                .repeatPassword(PASSWORD)
                .build();

        when(clientRepository.findClientByName(CLIENT_NAME)).thenReturn(Optional.of(new Client()));

        assertThrows(ClientAlreadyExistsException.class, () -> service.add(dto));
    }

    @Test
    public void shouldAddClientWhenDtoCorrect() {
        ClientCreateDto dto = ClientCreateDto.builder()
                .name(CLIENT_NAME)
                .password(PASSWORD)
                .repeatPassword(PASSWORD)
                .build();
        Client client = new Client();
        client.setName(CLIENT_NAME);
        client.setPassword(ENCODED_PASSWORD);
        client.setRole(Role.ROLE_USER);

        Client savedClient = new Client();
        savedClient.setId(ID);
        savedClient.setName(CLIENT_NAME);
        savedClient.setPassword(ENCODED_PASSWORD);
        savedClient.setRole(Role.ROLE_USER);

        when(clientRepository.findClientByName(CLIENT_NAME)).thenReturn(Optional.empty());
        when(encoder.encode(PASSWORD)).thenReturn(ENCODED_PASSWORD);
        when(clientRepository.save(client)).thenReturn(savedClient);

        assertThat(ID, is(service.add(dto)));

        verify(clientRepository).findClientByName(CLIENT_NAME);
        verify(encoder).encode(PASSWORD);
    }

    @Test
    public void shouldThrowRoleParseExceptionWhenTrySetRole() {
        RoleDto dto = RoleDto.builder()
                .clientId(ID)
                .role(INCORRECT_ROLE)
                .build();

        assertThrows(RoleParseException.class, () -> service.setRole(dto));
    }

    @Test
    public void shouldThrowClientNotFoundExceptionWhenTrySetRoleWithIncorrectClientId() {
        RoleDto dto = RoleDto.builder()
                .role(USER_ROLE)
                .clientId(null)
                .build();

        assertThrows(ClientNotFoundException.class, () -> service.setRole(dto));
    }

    @Test
    public void shouldThrowClientNotFoundExceptionWhenTrySetRole() {
        RoleDto dto = RoleDto.builder()
                .clientId(ID)
                .role(USER_ROLE)
                .build();

        when(clientRepository.findById(ID)).thenReturn(Optional.empty());

        assertThrows(ClientNotFoundException.class, () -> service.setRole(dto));
    }

    @Test
    public void shouldSetRoleWhenDtoCorrect() {
        RoleDto dto = RoleDto.builder()
                .clientId(ID)
                .role(USER_ROLE)
                .build();

        Client clientFromDb = new Client();
        clientFromDb.setName(CLIENT_NAME);
        clientFromDb.setRole(Role.ROLE_USER);
        clientFromDb.setPassword(PASSWORD);
        clientFromDb.setId(ID);

        when(clientRepository.findById(ID)).thenReturn(Optional.of(clientFromDb));
        when(clientRepository.save(clientFromDb)).thenReturn(clientFromDb);

        assertThat(Role.ROLE_USER.name(), is(service.setRole(dto)));

        verify(clientRepository).findById(ID);
    }
}
