package com.example.demo.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ClientCreateDto {
    String name;
    String password;
    String repeatPassword;
}
