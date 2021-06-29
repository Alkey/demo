package com.example.demo.service.impl;

import com.example.demo.dto.PolygonWithAreaDto;
import com.example.demo.entity.Point;
import com.example.demo.entity.Polygon;
import com.example.demo.repository.PolygonRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class PolygonServiceImplTest {
    private static final long ID = 1;
    private static final String NAME = "Polygon";
    private static final String GEOMETRY = "[[[28.507118225,49.267132467],[28.510894775,49.260355109],[28.516817093,49.264612071],[28.507118225,49.267132467]]]";
    private static final double AREA = 123.122;
    private final PolygonRepository repository = mock(PolygonRepository.class);
    private final ObjectMapper mapper = spy(ObjectMapper.class);
    private final PolygonServiceImpl service = new PolygonServiceImpl(repository, mapper);

    @Test
    public void shouldReturnEmptyOptionalWhenFindByIdNotExistingPolygon() throws JsonProcessingException {
        when(repository.findById(ID)).thenReturn(Optional.empty());
        assertThat(service.findById(ID), is(Optional.empty()));
    }

    @Test
    public void shouldReturnPolygonWithAreaDtoWhenFindByIdPolygon() throws JsonProcessingException {
        Optional<Polygon> optionalPolygon = Optional.of(new Polygon(ID, NAME, GEOMETRY, AREA));
        List<List<Point>> points = List.of(List.of(
                new Point(28.507118225, 49.267132467),
                new Point(28.510894775, 49.260355109),
                new Point(28.516817093, 49.264612071),
                new Point(28.507118225, 49.267132467)));
        Optional<PolygonWithAreaDto> dto = Optional.of(new PolygonWithAreaDto(NAME, points, AREA));

        when(repository.findById(ID)).thenReturn(optionalPolygon);

        assertThat(service.findById(ID), is(dto));
    }
}
