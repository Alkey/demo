package com.example.demo.controller;

import com.example.demo.dto.LineDto;
import com.example.demo.dto.LineWithLengthDto;
import com.example.demo.entity.Point;
import com.example.demo.service.LineService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class LineControllerUnitTest {
    private static final long ID = 1;
    private static final LineDto LINE_DTO = new LineDto("NAME", new Point(0, 1), new Point(1, 1));
    private static final String GET_LINE_ENDPOINT = "/line/{id}";
    private final LineService service = mock(LineService.class);
    private final LineController controller = new LineController(service);
    private final MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void addLineTest() throws Exception {

        when(service.add(LINE_DTO)).thenReturn(true);
        mockMvc.perform(post("/line")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(LINE_DTO)))
                .andExpect(status().isOk());
    }

    @Test
    public void addLineBadRequestTest() throws Exception {
        when(service.add(LINE_DTO)).thenReturn(false);
        mockMvc.perform(post("/line")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(LINE_DTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getLineTest() throws Exception {
        LineWithLengthDto dto = new LineWithLengthDto(LINE_DTO.getName(), LINE_DTO.getStartPoint(),
                LINE_DTO.getEndPoint(), 1);
        when(service.findById(ID)).thenReturn(Optional.of(dto));
        mockMvc.perform(get(GET_LINE_ENDPOINT, ID))
                .andExpect(status().isOk());
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
        when(service.getLineStringIntersection(ID, 2)).thenReturn(Optional.of(new Point(1, 1)));
        mockMvc.perform(get("/line/intersection")
                .param("firstId", "1")
                .param("secondId", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lat").value(1))
                .andExpect(jsonPath("$.lon").value(1));
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
