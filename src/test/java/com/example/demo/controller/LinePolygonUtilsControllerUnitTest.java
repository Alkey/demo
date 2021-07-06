package com.example.demo.controller;

import com.example.demo.service.LinePolygonUtilsService;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class LinePolygonUtilsControllerUnitTest {
    private static final String LINE = "lineId";
    private static final String POLYGON = "polygonId";
    private static final long LINE_ID = 1;
    private static final long POLYGON_ID = 2;
    private final LinePolygonUtilsService service = mock(LinePolygonUtilsService.class);
    private final LinePolygonUtilsController controller = new LinePolygonUtilsController(service);
    private final MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

    @Test
    public void isIntersectsTest() throws Exception {
        when(service.isIntersects(LINE_ID, POLYGON_ID)).thenReturn(true);
        mockMvc.perform(get("/intersects")
                .param(LINE, "1")
                .param(POLYGON, "2"))
                .andExpect(status().isOk());
    }

    @Test
    public void isWithinTest() throws Exception {
        when(service.isWithIn(LINE_ID, POLYGON_ID)).thenReturn(false);
        mockMvc.perform(get("/within")
                .param(LINE, "1")
                .param(POLYGON, "2"))
                .andExpect(status().isOk());
    }

    @Test
    public void getDistanceTest() throws Exception {
        when(service.getDistance(LINE_ID, POLYGON_ID)).thenReturn(4.0);
        mockMvc.perform(get("/within")
                .param(LINE, "1")
                .param(POLYGON, "2"))
                .andExpect(status().isOk());
    }
}
