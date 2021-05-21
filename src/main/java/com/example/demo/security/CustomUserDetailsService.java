package com.example.demo.security;

import com.example.demo.entity.Client;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final ClientRepository clientRepository;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Client client = clientRepository.findClientByName(name).orElseThrow(() -> new UserNotFoundException("User not found"));
        List<GrantedAuthority> authorities = client.getRoles().stream()
                .map(r -> new SimpleGrantedAuthority(r.getRoleName().name()))
                .collect(Collectors.toList());
        return new User(client.getName(), client.getPassword(), authorities);
    }
}
