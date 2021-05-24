package com.example.demo.dto;

import lombok.Value;

import java.util.List;

@Value
public class ExceptionDto {
    List<String> messages;
    String exceptionName;
}
