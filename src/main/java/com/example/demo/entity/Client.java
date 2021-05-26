package com.example.demo.entity;

import lombok.Value;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Value
public class Client {
    Long id;
    String name;
    String password;
    @Enumerated(EnumType.STRING)
    Role role;
}
