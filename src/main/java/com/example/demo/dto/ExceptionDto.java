package com.example.demo.dto;

import lombok.Data;

import java.util.List;

@Data
public class ExceptionDto {
    private List<String> messages;
    private String exceptionName;
}
