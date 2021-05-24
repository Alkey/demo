package com.example.demo.security;

import com.example.demo.entity.Client;
import com.example.demo.exception.ClientNotFoundException;
import com.example.demo.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final ClientRepository clientRepository;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Client client = clientRepository.findClientByName(name).orElseThrow(() -> new ClientNotFoundException("User not found"));
        return new User(client.getName(), client.getPassword(), List.of(new SimpleGrantedAuthority(client.getRole().name())));
    }
}
