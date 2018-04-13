package com.xy.inc.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.xy.inc.repository.PoiRepository;
import com.xy.inc.service.PoiService;
import com.xy.inc.service.dto.PoiDTO;
import com.xy.inc.service.exception.PoiException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class PoiControllerTest {

    @Autowired
    private MockMvc mock;

    @MockBean
    private PoiService service;

    @Autowired
    private PoiRepository poiRepository;

    private PoiDTO poiDTO;

    @Before
    public void setUp() {
        poiDTO = new PoiDTO();
        poiDTO.setName("poiDTO");
        poiDTO.setX(12);
        poiDTO.setY(21);
    }

    @Test
    public void listAllPoisByApi() throws Exception {

        List<PoiDTO> poiDTOList = Arrays.asList(poiDTO);

        given(service.getAll()).willReturn(poiDTOList);

        mock.perform(get("/api/pois")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.[*].name").value(hasItem(poiDTO.getName())));
    }

    @Test
    public void nonExistingUrl() throws Exception {
        mock.perform(get("/api/non-existing")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void saveSameNamePoi() throws Exception {
        given(service.save(poiDTO)).willThrow(new PoiException("same name poi"));

        mock.perform(post("/api/pois")
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertObjectToJsonBytes(poiDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void listByProximit() throws Exception {
        List<PoiDTO> poiDTOList = Arrays.asList(poiDTO);

        given(service.listByProximmity(15, 20, 10)).willReturn(poiDTOList);

        mock.perform(get("/api/pois/{x}/{y}/proximmity/{max}", 15,20, 10)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.[*].name").value(hasItem(poiDTO.getName())));
    }

    @Test
    public void listByProximitNegativeCoords() throws Exception {
        given(service.listByProximmity(-15, -20, 10)).willReturn(null);

        mock.perform(get("/api/pois/{x}/{y}/proximmity/{max}", 15,20, 10)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void saveTest() throws Exception {
        given(service.save(poiDTO)).willReturn(poiDTO);

        mock.perform(post("/api/pois")
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertObjectToJsonBytes(poiDTO)))
                .andExpect(status().isCreated());
    }

    public static byte[] convertObjectToJsonBytes(Object object)
            throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        JavaTimeModule module = new JavaTimeModule();
        mapper.registerModule(module);

        return mapper.writeValueAsBytes(object);
    }

}
