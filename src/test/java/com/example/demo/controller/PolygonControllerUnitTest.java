package com.example.demo.controller;

import com.example.demo.dto.PolygonWithAreaDto;
import com.example.demo.entity.GeoJsonGeometry;
import com.example.demo.entity.GeoJsonPolygonGeometry;
import com.example.demo.entity.Point;
import com.example.demo.service.PolygonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static com.example.demo.util.ObjectMapperUtil.getMapper;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PolygonControllerUnitTest {
    private static final String URL = "/polygon";
    private static final long ID = 1;
    private static final String NAME = "name";
    private static final List<List<List<Double>>> COORDINATES = List.of(
            List.of(
                    List.of(1.5, 1.0),
                    List.of(1.0, 1.0),
                    List.of(1.5, 1.5),
                    List.of(2.0, 1.5),
                    List.of(1.5, 1.0)));
    private static final List<List<Point>> POINTS = List.of(
            List.of(
                    new Point(1.5, 1.0),
                    new Point(1.0, 1.0),
                    new Point(1.5, 1.5),
                    new Point(2.0, 1.5),
                    new Point(1.5, 1.0)));
    private final PolygonService service = mock(PolygonService.class);
    private final PolygonController controller = new PolygonController(service);
    private final MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    private final ObjectMapper mapper = getMapper();

    @Test
    public void addPolygonTest() throws Exception {
        GeoJsonPolygonGeometry geometry = new GeoJsonPolygonGeometry(COORDINATES);
        when(service.add(geometry)).thenReturn(true);
        mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(geometry)))
                .andExpect(status().isOk());
    }

    @Test
    public void addPolygonBadRequestTest() throws Exception {
        GeoJsonPolygonGeometry geometry = new GeoJsonPolygonGeometry(COORDINATES);
        when(service.add(geometry)).thenReturn(false);
        mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(geometry)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getPolygonTest() throws Exception {
        PolygonWithAreaDto dto = new PolygonWithAreaDto(NAME, POINTS, 122.2);
        when(service.findById(ID)).thenReturn(Optional.of(dto));
        String content = mockMvc.perform(get(URL + "/{id}", ID))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        PolygonWithAreaDto result = mapper.readValue(content, PolygonWithAreaDto.class);
        assertThat(result, is(dto));
    }

    @Test
    public void getPolygonBadRequestTest() throws Exception {
        when(service.findById(ID)).thenReturn(Optional.empty());
        mockMvc.perform(get(URL + "/{id}", ID))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getPolygonIntersectionTest() throws Exception {
        GeoJsonGeometry polygon = new GeoJsonPolygonGeometry(COORDINATES);
        when(service.getPolygonIntersection(ID, 2)).thenReturn(polygon);
        String content = mockMvc.perform(get(URL + "/intersection")
                .param("firstId", "1")
                .param("secondId", "2"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        GeoJsonGeometry result = mapper.readValue(content, GeoJsonPolygonGeometry.class);
        assertThat(result, is(polygon));
    }
}
