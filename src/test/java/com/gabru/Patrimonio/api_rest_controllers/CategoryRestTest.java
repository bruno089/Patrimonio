package com.gabru.Patrimonio.api_rest_controllers;

import com.gabru.Patrimonio.api.rest.CategoryRest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CategoryRestTest {
    @Mock
    CategoryRest categoryRest;
    MockMvc mockMvc;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(categoryRest).build();
    }

    @Test
    void buscarTodos () throws Exception {
        this.mockMvc.perform(get("/categories")
                        .content("")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    void buscarTodosNotFound () throws Exception {
        this.mockMvc.perform(get("/CON")
                        .content("")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound());
    }


}
