package com.example.demo.config;

import com.example.demo.dto.ExceptionDto;
import com.example.demo.exception.PasswordMismatchException;
import com.example.demo.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@RestControllerAdvice
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({
            PasswordMismatchException.class,
            UserNotFoundException.class,
            RuntimeException.class
    })
    public ExceptionDto handleException(RuntimeException exception) {
        ExceptionDto dto = new ExceptionDto();
        dto.setExceptionName(exception.getClass().getSimpleName());
        dto.setMessages(List.of(exception.getMessage()));
        return dto;
    }
}
