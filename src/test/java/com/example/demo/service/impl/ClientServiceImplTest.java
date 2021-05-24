package com.example.demo.service.impl;

import com.example.demo.dto.ClientCreateDto;
import com.example.demo.entity.Client;
import com.example.demo.entity.Role;
import com.example.demo.exception.ClientAlreadyExistsException;
import com.example.demo.exception.PasswordMismatchException;
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
}
