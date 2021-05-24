package com.example.demo.config;

import com.example.demo.dto.ExceptionDto;
import com.example.demo.exception.ClientAlreadyExistsException;
import com.example.demo.exception.ClientNotFoundException;
import com.example.demo.exception.PasswordMismatchException;
import com.example.demo.exception.RoleParseException;
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
            ClientAlreadyExistsException.class,
            ClientNotFoundException.class,
            RoleParseException.class
    })
    public ExceptionDto handleException(RuntimeException exception) {
        return new ExceptionDto(List.of(exception.getMessage()), exception.getClass().getSimpleName());
    }
}
