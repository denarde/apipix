package com.denarde.apipix.rest.controller;

import com.denarde.apipix.domain.entity.ReceivedPix;
import com.denarde.apipix.domain.repository.KeysPix;
import com.denarde.apipix.domain.repository.ReceivedsPix;
import com.denarde.apipix.rest.dto.ReceivedPixDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(controllers = ReceivedPixController.class)
@AutoConfigureMockMvc
public class ReceivedPixControllerTest {

    static String RECEIVED_PIX_API = "/api/receivedPix";


    @Autowired
    MockMvc mvc;

    @MockBean
    KeysPix keysPixRepository;

    @MockBean
    ReceivedsPix receivedsPix;

    @Test
    @DisplayName("save receivedPix successfully.")
    public void saveReceivedPixTest() throws Exception {

        ReceivedPix receivedPix = createReceivedPix("41634836839", 250.00);

        ReceivedPixDTO dto = createReceivedPixDto("41634836839", 250.00);

        BDDMockito.given(keysPixRepository.existsByKey(Mockito.any(String.class))).willReturn(true);
        BDDMockito.given(receivedsPix.save(Mockito.any(ReceivedPix.class))).willReturn(receivedPix);

        String json = new ObjectMapper().writeValueAsString(dto);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(RECEIVED_PIX_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc
                .perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("key").value(dto.getKey()))
                .andExpect(jsonPath("value").value(dto.getValue()));

    }

    @Test
    @DisplayName("not save receivedPix because not exists Key.")
    public void notSaveReceivedPixNotExistsKeyTest() throws Exception {

        ReceivedPix receivedPix = createReceivedPix("41634836839", 250.00);

        ReceivedPixDTO dto = createReceivedPixDto("41634836839", 250.00);

        BDDMockito.given(keysPixRepository.existsByKey(Mockito.any(String.class))).willReturn(false);
        BDDMockito.given(receivedsPix.save(Mockito.any(ReceivedPix.class))).willReturn(receivedPix);

        String json = new ObjectMapper().writeValueAsString(dto);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(RECEIVED_PIX_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc
                .perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errors").value("key not registered at the institution Will Bank."));

    }

    @Test
    @DisplayName("not save receivedPix because value is equals 0.")
    public void notSaveReceivedPixValueNotValidTest() throws Exception {

        ReceivedPix receivedPix = createReceivedPix("41634836839", 0d);

        ReceivedPixDTO dto = createReceivedPixDto("41634836839", 0d);

        BDDMockito.given(keysPixRepository.existsByKey(Mockito.any(String.class))).willReturn(false);
        BDDMockito.given(receivedsPix.save(Mockito.any(ReceivedPix.class))).willReturn(receivedPix);

        String json = new ObjectMapper().writeValueAsString(dto);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(RECEIVED_PIX_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc
                .perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errors").value("Value must be greater than 0."));

    }

    private ReceivedPixDTO createReceivedPixDto(String cpf, Double value) {
        return ReceivedPixDTO.builder().key(cpf).value(value).build();
    }

    private ReceivedPix createReceivedPix(String cpf, Double value) {
        return ReceivedPix.builder().id(10).key(cpf).value(value).build();
    }

}
