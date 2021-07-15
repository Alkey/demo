package com.example.demo.controller;

import com.example.demo.dto.LineWithLengthDto;
import com.example.demo.entity.GeoJsonLineGeometry;
import com.example.demo.entity.Point;
import com.example.demo.service.LineService;
import com.fasterxml.jackson.core.JsonProcessingException;
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

public class LineControllerUnitTest {
    private static final long ID = 1;
    private static final GeoJsonLineGeometry GEOMETRY = new GeoJsonLineGeometry(List.of(
            List.of(0.0, 1.0),
            List.of(1.0, 1.0)));
    private static final String GET_LINE_ENDPOINT = "/line/{id}";
    private final LineService service = mock(LineService.class);
    private final LineController controller = new LineController(service);
    private final MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    private final ObjectMapper mapper = getMapper();

    @Test
    public void addLineTest() throws Exception {
        when(service.add(GEOMETRY)).thenReturn(true);
        mockMvc.perform(post("/line")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(GEOMETRY)))
                .andExpect(status().isOk());
    }

    @Test
    public void addLineBadRequestTest() throws Exception {
        when(service.add(GEOMETRY)).thenReturn(false);
        mockMvc.perform(post("/line")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(GEOMETRY)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getLineTest() throws Exception {
        LineWithLengthDto dto = new LineWithLengthDto(GEOMETRY.getType(), new Point(0, 1),
                new Point(1, 1), 1);
        when(service.findById(ID)).thenReturn(Optional.of(dto));
        String content = mockMvc.perform(get(GET_LINE_ENDPOINT, ID))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        LineWithLengthDto result = mapper.readValue(content, LineWithLengthDto.class);
        assertThat(result, is(dto));
    }

    @Test
    public void getNotExistLineTest() throws Exception {
        when(service.findById(ID)).thenReturn(Optional.empty());
        mockMvc.perform(get(GET_LINE_ENDPOINT, ID))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getLineThrowJsonParseExceptionTest() throws Exception {
        when(service.findById(ID)).thenThrow(JsonProcessingException.class);
        mockMvc.perform(get(GET_LINE_ENDPOINT, ID))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getLineStringIntersectionTest() throws Exception {
        Point point = new Point(1, 1);
        when(service.getLineStringIntersection(ID, 2)).thenReturn(Optional.of(point));
        String content = mockMvc.perform(get("/line/intersection")
                .param("firstId", "1")
                .param("secondId", "2"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        Point result = mapper.readValue(content, Point.class);
        assertThat(result, is(point));
    }

    @Test
    public void getLineStringIntersectionBadRequestTest() throws Exception {
        when(service.getLineStringIntersection(ID, 2)).thenReturn(Optional.empty());
        mockMvc.perform(get("/line/intersection")
                .param("firstId", "1")
                .param("secondId", "2"))
                .andExpect(status().isBadRequest());
    }
}
