package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import org.springframework.lang.NonNull;

import javax.validation.constraints.Size;

@Value
public class ClientCreateDto {
    @Size(min = 1)
    @NonNull
    String name;
    String password;
    String repeatPassword;

    @JsonCreator
    public ClientCreateDto(@JsonProperty("name") @NonNull String name,
                           @JsonProperty("password") String password,
                           @JsonProperty("repeatPassword") String repeatPassword) {
        this.name = name;
        this.password = password;
        this.repeatPassword = repeatPassword;
    }
}
