package com.example.demo.controller;

import com.example.demo.service.LinePolygonUtilsService;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class LinePolygonUtilsControllerUnitTest {
    private static final String LINE_PARAMETER = "lineId";
    private static final String POLYGON_PARAMETER = "polygonId";
    private static final long LINE_ID = 1;
    private static final long POLYGON_ID = 2;
    private final LinePolygonUtilsService service = mock(LinePolygonUtilsService.class);
    private final LinePolygonUtilsController controller = new LinePolygonUtilsController(service);
    private final MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

    @Test
    public void isIntersectsTest() throws Exception {
        when(service.isIntersects(LINE_ID, POLYGON_ID)).thenReturn(true);
        mockMvc.perform(get("/intersects")
                .param(LINE_PARAMETER, "1")
                .param(POLYGON_PARAMETER, "2"))
                .andExpect(status().isOk());
    }

    @Test
    public void isWithinTest() throws Exception {
        when(service.isWithIn(LINE_ID, POLYGON_ID)).thenReturn(false);
        mockMvc.perform(get("/within")
                .param(LINE_PARAMETER, "1")
                .param(POLYGON_PARAMETER, "2"))
                .andExpect(status().isOk());
    }

    @Test
    public void getDistanceTest() throws Exception {
        double distance = 4.0;
        when(service.getDistance(LINE_ID, POLYGON_ID)).thenReturn(distance);
        double result = Double.parseDouble(mockMvc.perform(get("/distance")
                .param(LINE_PARAMETER, "1")
                .param(POLYGON_PARAMETER, "2"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString());
        assertThat(result, is(distance));
    }
}
