package org.resource.api.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.resource.api.rest.dto.AssetDto;
import org.resource.model.AssetType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class AssetControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateAssetSuccessfully() throws Exception {
        AssetDto asset = new AssetDto(1L, "Bitcoin", "Btc", "BTC crypto", AssetType.CRYPTOCURRENCY.name(), 30000.0, null);

        mockMvc.perform(post("/api/assets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(asset)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Bitcoin"))
                .andExpect(jsonPath("$.symbol").value("Btc"))
                .andExpect(jsonPath("$.description").value("BTC crypto"))
                .andExpect(jsonPath("$.type").value(AssetType.CRYPTOCURRENCY.getDisplayName()))
                .andExpect(jsonPath("$.price").value(30000.0))
                .andExpect(jsonPath("$.owner").doesNotExist());
    }
}

