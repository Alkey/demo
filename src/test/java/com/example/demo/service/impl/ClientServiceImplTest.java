package com.example.demo.service.impl;

import com.example.demo.dto.ClientCreateDto;
import com.example.demo.entity.Client;
import com.example.demo.entity.Role;
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
    private static final long ID = 1L;
    private final ClientRepository clientRepository = mock(ClientRepository.class);
    private final PasswordEncoder encoder = mock(PasswordEncoder.class);
    private final ClientServiceImpl service = new ClientServiceImpl(clientRepository, encoder);

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenTryAddClient() {
        ClientCreateDto dto = ClientCreateDto.builder()
                .name(CLIENT_NAME)
                .password(PASSWORD)
                .repeatPassword("1234")
                .build();

        assertThrows(IllegalArgumentException.class, () -> service.add(dto));
    }

    @Test
    public void shouldAddClientWhenDtoCorrect() {
        ClientCreateDto dto = ClientCreateDto.builder()
                .name(CLIENT_NAME)
                .password(PASSWORD)
                .repeatPassword(PASSWORD)
                .build();
        Client client = new Client(null, CLIENT_NAME, ENCODED_PASSWORD, Role.USER);

        when(clientRepository.findByName(CLIENT_NAME)).thenReturn(Optional.empty());
        when(encoder.encode(PASSWORD)).thenReturn(ENCODED_PASSWORD);
        when(clientRepository.add(client)).thenReturn(ID);

        assertThat(ID, is(service.add(dto)));

        verify(clientRepository).findByName(CLIENT_NAME);
        verify(encoder).encode(PASSWORD);
    }
}
