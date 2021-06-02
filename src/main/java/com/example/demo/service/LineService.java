package com.example.demo.service;

import com.example.demo.dto.LineDto;
import com.example.demo.dto.LineWithLengthDto;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Optional;

public interface LineService {

    boolean add(LineDto dto);

    Optional<LineWithLengthDto> findById(long id) throws JsonProcessingException;
}
