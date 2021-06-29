package com.example.demo.service.impl;

import com.example.demo.dto.LineWithLengthDto;
import com.example.demo.entity.Line;
import com.example.demo.entity.Point;
import com.example.demo.repository.LineRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class LineServiceImplTest {
    private static final long ID = 1;
    private static final String NAME = "Line";
    private static final double LENGTH = 123.122;
    private static final String GEOMETRY = "[[28.494243622,49.283259954],[28.500595093,49.279004601]]";


    private final LineRepository repository = mock(LineRepository.class);
    private final ObjectMapper mapper = spy(ObjectMapper.class);
    private final LineServiceImpl service = new LineServiceImpl(repository, mapper);

    @Test
    public void shouldReturnEmptyOptionalWhenFindByIdNotExistingLine() throws JsonProcessingException {
        when(repository.findById(ID)).thenReturn(Optional.empty());
        assertThat(service.findById(ID), is(Optional.empty()));
    }

    @Test
    public void shouldReturnLineWithLengthDtoWhenFindByIdLine() throws JsonProcessingException {
        Optional<Line> optionalLine = Optional.of(new Line(ID, NAME, GEOMETRY, LENGTH));
        Point startPoint = new Point(28.494243622, 49.283259954);
        Point endPoint = new Point(28.500595093, 49.279004601);
        Optional<LineWithLengthDto> dto = Optional.of(new LineWithLengthDto(NAME, startPoint, endPoint, LENGTH));

        when(repository.findById(ID)).thenReturn(optionalLine);

        assertThat(service.findById(ID), is(dto));
    }
}
