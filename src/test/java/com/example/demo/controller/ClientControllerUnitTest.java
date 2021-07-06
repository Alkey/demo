package com.example.demo.controller;

import com.example.demo.dto.ClientCreateDto;
import com.example.demo.entity.Role;
import com.example.demo.service.ClientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.example.demo.util.ObjectMapperUtil.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ClientControllerUnitTest {
    private static final String SET_ROLE_ENDPOINT = "/clients/set-role/{clientId}/{role}";
    private static final long ID = 1;
    private final ClientService service = mock(ClientService.class);
    private final ClientController controller = new ClientController(service);
    private final MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    private final ObjectMapper mapper = getMapper();

    @Test
    public void createClientTest() throws Exception {
        ClientCreateDto dto = new ClientCreateDto("User", "123", "123");
        when(service.add(dto)).thenReturn(ID);
        long content = Long.parseLong(mockMvc.perform(post("/clients/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(getMapper().writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString());
        assertThat(content, is(ID));
    }

    @Test
    public void setRoleClientTest() throws Exception {
        when(service.setRole(ID, Role.ADMIN)).thenReturn(true);
        mockMvc.perform(put(SET_ROLE_ENDPOINT, ID, Role.ADMIN))
                .andExpect(status().isOk());
    }

    @Test
    public void setRoleClientBadRequestTest() throws Exception {
        when(service.setRole(1, Role.ADMIN)).thenReturn(false);
        mockMvc.perform(put(SET_ROLE_ENDPOINT, ID, Role.ADMIN))
                .andExpect(status().isBadRequest());
    }
}
