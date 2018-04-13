package com.xy.inc.service;

import com.xy.inc.domain.Poi;
import com.xy.inc.repository.PoiRepository;
import com.xy.inc.service.dto.PoiDTO;
import com.xy.inc.service.impl.PoiServiceImpl;
import com.xy.inc.service.mapper.PoiMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
public class PoiServiceImplTest {

    @TestConfiguration
    static class PoiServiceImplTextConfig {
        @Autowired
        private PoiMapper poiMapper;

        @Autowired
        private PoiRepository poiRepository;

        @Bean
        public PoiService poiService() {
            return new PoiServiceImpl(poiRepository, poiMapper);
        }
    }

    @Autowired
    private PoiMapper poiMapper;

    @Autowired
    private PoiRepository poiRepository;

    @Autowired
    private PoiService poiService;

    private Poi poi;

    @Before
    public void setUp() {
        poi = new Poi();
        poi.setName("Service Poi");
        poi.setY(23);
        poi.setX(18);

        poiRepository.saveAndFlush(poi);

        Mockito.when(poiRepository.getPoiByName(poi.getName())).thenReturn(poi);

        List<Poi> poiList = new ArrayList<>();
        poiList.add(poi);
        poi.setName("poi 2");
        poiList.add(poi);
        Mockito.when(poiRepository.findAll()).thenReturn(poiList);

        Mockito.when(poiRepository.save(poi)).thenReturn(poi);
    }


    @Test
    public void getAllTest() {
        List<PoiDTO> poiList = poiService.getAll();
        assertThat(poiList).contains(poiMapper.poiToPoiDTO(poi));
    }
}
