package com.ecb.foreign_exchange_rate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ForexControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetForexRate() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/fx?targetCurrency=INR")) // Use MockMvcRequestBuilders.get for GET request
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.targetCurrency").value("INR"));
    }

    @Test
    public void testGetLatestForexRates() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/fx/INR")) // Use MockMvcRequestBuilders.get for GET request
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }
}

