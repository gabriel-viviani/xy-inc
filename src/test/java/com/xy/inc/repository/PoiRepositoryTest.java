package com.xy.inc.repository;

import com.xy.inc.domain.Poi;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PoiRepositoryTest {

    @Autowired
    private PoiRepository poiRepository;

    private Poi poi;

    @Autowired
    TestEntityManager testEntityManager;

    @Before
    public void setUp() throws Exception {
        poi = new Poi();
        poi.setY(1);
        poi.setX(2);
        poi.setName("name");
        poiRepository.saveAndFlush(poi);
    }

    @Test(expected = ConstraintViolationException.class)
    public void deleteNullId() throws Exception {
        poiRepository.delete(new Poi());
    }

    @Test(expected = ConstraintViolationException.class)
    public void insertNull() throws Exception {
        poiRepository.save(new Poi());
    }

    @Test
    public void findByName() throws Exception {
        testEntityManager.persist(poi);
        testEntityManager.flush();

        Poi poiByName = poiRepository.getPoiByName(poi.getName());
        assertThat(poiByName.getName()).isEqualTo(poi.getName());
    }

    @Test
    public void insertPoi() throws Exception {
        poi.setName("InsertTest2");
        poiRepository.saveAndFlush(poi);

        Poi insertedPoi = poiRepository.getPoiByName(poi.getName());

        assertThat(insertedPoi).isEqualTo(poi);
    }

}
