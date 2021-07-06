package com.example.demo.controller;

import com.example.demo.entity.GeoJsonGeometry;
import com.example.demo.entity.GeoJsonPolygonGeometry;
import com.example.demo.service.GeoJsonGeometryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.example.demo.util.ObjectMapperUtil.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class GeoJsonControllerUnitTest {
    private static final String URL = "/geojson";
    private static final GeoJsonGeometry GEOMETRY = new GeoJsonPolygonGeometry(List.of(
            List.of(
                    List.of(1.5, 1.0),
                    List.of(1.0, 1.0),
                    List.of(1.5, 1.5),
                    List.of(2.0, 1.5),
                    List.of(1.5, 1.0))));
    private final GeoJsonGeometryService service = mock(GeoJsonGeometryService.class);
    private final GeoJsonController controller = new GeoJsonController(service);
    private final MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    private final ObjectMapper mapper = getMapper();

    @Test
    public void saveGeometryTest() throws Exception {
        when(service.add(GEOMETRY)).thenReturn(true);
        mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(GEOMETRY)))
                .andExpect(status().isOk());
    }

    @Test
    public void saveGeometryBadRequestTest() throws Exception {
        when(service.add(GEOMETRY)).thenReturn(false);
        mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(GEOMETRY)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void restoreGeometriesTest() throws Exception {
        when(service.restore()).thenReturn(true);
        mockMvc.perform(post(URL + "/restore"))
                .andExpect(status().isOk());
    }

    @Test
    public void restoreGeometriesBadRequestTest() throws Exception {
        when(service.restore()).thenReturn(false);
        mockMvc.perform(post(URL + "/restore"))
                .andExpect(status().isBadRequest());
    }
}
