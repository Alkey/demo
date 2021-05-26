package com.example.demo.dto;

import lombok.Builder;
import lombok.Value;
import org.springframework.lang.NonNull;

import javax.validation.constraints.Size;

@Value
@Builder
public class ClientCreateDto {
    @Size(min = 1)
    @NonNull
    String name;
    String password;
    String repeatPassword;
}
