package com.denarde.apipix.rest.controller;

import com.denarde.apipix.domain.entity.KeyPix;
import com.denarde.apipix.domain.entity.ReceivedPix;
import com.denarde.apipix.domain.enums.KeyType;
import com.denarde.apipix.domain.repository.KeysPix;
import com.denarde.apipix.domain.repository.ReceivedsPix;
import com.denarde.apipix.rest.dto.ReceivedPixDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(controllers = KeyPixController.class)
@AutoConfigureMockMvc
public class KeyPixControllerTest {

    static String KEY_PIX_API = "/api/keypix";

    @Autowired
    MockMvc mvc;

    @MockBean
    KeysPix keysPixRepository;

    @Test
    @DisplayName("save keyPix successfully.")
    public void saveKeyPixTest() throws Exception {

        KeyPix keyPix = createPixCpf(10, "41634836839");

        BDDMockito.given(keysPixRepository.save(Mockito.any(KeyPix.class))).willReturn(keyPix);

        String json = new ObjectMapper().writeValueAsString(keyPix);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(KEY_PIX_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc
                .perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").value(keyPix.getId()))
                .andExpect(jsonPath("key").value(keyPix.getKey()))
                .andExpect(jsonPath("keyType").value(keyPix.getKeyType().toString()));

    }

    @Test
    @DisplayName("not save keyPix because key empty in json.")
    public void notSaveKeyPixJsonEmptyKeyTest() throws Exception {

        KeyPix keyPix = KeyPix.builder().keyType(KeyType.CPF).build();

        BDDMockito.given(keysPixRepository.save(Mockito.any(KeyPix.class))).willReturn(keyPix);

        String json = new ObjectMapper().writeValueAsString(keyPix);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(KEY_PIX_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc
                .perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errors").value("{field.key.mandatory}"));

    }

    @Test
    @DisplayName("not save keyPix because keyType empty in json.")
    public void notSaveKeyPixJsonEmptyKeyTypeTest() throws Exception {

        KeyPix keyPix = KeyPix.builder().key("41634836838").build();

        BDDMockito.given(keysPixRepository.save(Mockito.any(KeyPix.class))).willReturn(keyPix);

        String json = new ObjectMapper().writeValueAsString(keyPix);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(KEY_PIX_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc
                .perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errors").value("{field.keyType.mandatory}"));

    }



    @Test
    @DisplayName("not save keyPix because cpf invalid.")
    public void notSaveKeyInvalidCpfPixTest() throws Exception {

        KeyPix keyPix = createPixCpf("416348");

        BDDMockito.given(keysPixRepository.save(Mockito.any(KeyPix.class))).willReturn(keyPix);

        String json = new ObjectMapper().writeValueAsString(keyPix);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(KEY_PIX_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc
                .perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errors").value("Invalid Key."));

    }

    @Test
    @DisplayName("not save keyPix because email invalid.")
    public void notSaveKeyPixInvalidEmailTest() throws Exception {

        KeyPix keyPix = createPixEmail("luizdenarde.com");

        BDDMockito.given(keysPixRepository.save(Mockito.any(KeyPix.class))).willReturn(keyPix);

        String json = new ObjectMapper().writeValueAsString(keyPix);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(KEY_PIX_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc
                .perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errors").value("Invalid Key."));

    }

    @Test
    @DisplayName("not save keyPix because exists.")
    public void notSaveKeyPixExistsTest() throws Exception {

        KeyPix keyPix = createPixEmail("luizdenarde@uol.com");

        BDDMockito.given(keysPixRepository.existsByKey(Mockito.any(String.class))).willReturn(true);
        BDDMockito.given(keysPixRepository.save(Mockito.any(KeyPix.class))).willReturn(keyPix);

        String json = new ObjectMapper().writeValueAsString(keyPix);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(KEY_PIX_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc
                .perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errors").value("Key already exists."));

    }

    @Test
    @DisplayName("update keyPix.")
    public void updateKeyPixTest() throws Exception {
        Integer id = 10;
        String json = new ObjectMapper().writeValueAsString(createPixCpf("41634836839"));

        KeyPix updatingKeyPix = KeyPix.builder().id(id).key("41634836839").keyType(KeyType.CPF).build();
        BDDMockito.given(keysPixRepository.findById(id)).willReturn(Optional.of(updatingKeyPix));
        KeyPix updatedKeyPix = KeyPix.builder().id(id).key("luiz_denarde@uol.com").keyType(KeyType.EMAIL).build();
        BDDMockito.given(keysPixRepository.save(Mockito.any(KeyPix.class))).willReturn(updatedKeyPix);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put(KEY_PIX_API.concat("/" + id))
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(status().isNoContent());

    }

    @Test
    @DisplayName("failed update keyPix not exists.")
    public void updateKeyPixNotExistsTest() throws Exception {
        Integer id = 10;
        String json = new ObjectMapper().writeValueAsString(createPixCpf("41634836839"));

        BDDMockito.given(keysPixRepository.findById(id)).willReturn(Optional.empty());
        KeyPix updatedKeyPix = KeyPix.builder().id(id).key("luiz_denarde@uol.com").keyType(KeyType.EMAIL).build();
        BDDMockito.given(keysPixRepository.save(Mockito.any(KeyPix.class))).willReturn(updatedKeyPix);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put(KEY_PIX_API.concat("/" + id))
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(status().isNotFound());

    }

    @Test
    @DisplayName("delete keyPix successfully.")
    public void deleteKeyPixTest() throws Exception {

        KeyPix keyPix = createPixCpf(10, "41634836839");

        BDDMockito.given(keysPixRepository.findById(Mockito.any(Integer.class))).willReturn(Optional.of(keyPix));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete(KEY_PIX_API.concat("/" + 10));

        mvc
                .perform(request)
                .andExpect(status().isNoContent());

    }

    @Test
    @DisplayName("failed delete keyPix not exists.")
    public void deleteKeyPixNotExistsTest() throws Exception {

        KeyPix keyPix = createPixCpf(10, "41634836839");

        BDDMockito.given(keysPixRepository.findById(Mockito.any(Integer.class))).willReturn(Optional.empty());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete(KEY_PIX_API.concat("/" + 10));

        mvc
                .perform(request)
                .andExpect(status().isNotFound());

    }



    @Test
    @DisplayName("get one keyPix by id.")
    public void getKeyPixTest() throws Exception {

        KeyPix keyPix = createPixCpf(10, "41634836839");

        BDDMockito.given(keysPixRepository.findById(Mockito.any(Integer.class))).willReturn(Optional.of(keyPix));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(KEY_PIX_API.concat("/" + 10));

        mvc
                .perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(keyPix.getId()))
                .andExpect(jsonPath("key").value(keyPix.getKey()))
                .andExpect(jsonPath("keyType").value(keyPix.getKeyType().toString()));
        ;

    }

    @Test
    @DisplayName("failed get one keyPix by id not exists.")
    public void getKeyPixNotExistsTest() throws Exception {

        KeyPix keyPix = createPixCpf(10, "41634836839");

        BDDMockito.given(keysPixRepository.findById(Mockito.any(Integer.class))).willReturn(Optional.empty());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(KEY_PIX_API.concat("/" + 10));

        mvc
                .perform(request)
                .andExpect(status().isNotFound());
        ;

    }

    @Test
    @DisplayName("get all keyPix.")
    public void getAllKeyPixTest() throws Exception {

        List<KeyPix> keysPix = List.of(createPixCpf(10, "41634836839"), createPixCpf(11, "55534836839"));

        BDDMockito.given(keysPixRepository.findAll()).willReturn(keysPix);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(KEY_PIX_API);

        mvc
                .perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(2)));


    }


    private KeyPix createPixCpf(Integer id, String cpf) {
        return KeyPix.builder().id(id).key(cpf).keyType(KeyType.CPF).build();
    }

    private KeyPix createPixCpf(String cpf) {
        return KeyPix.builder().key(cpf).keyType(KeyType.CPF).build();
    }

    private KeyPix createPixEmail(String email) {
        return KeyPix.builder().key(email).keyType(KeyType.EMAIL).build();
    }


}
