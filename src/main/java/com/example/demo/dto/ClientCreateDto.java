package com.example.demo.dto;

import lombok.Data;

@Data
public class ClientCreateDto {
    private String name;
    private String password;
    private String repeatPassword;
}
