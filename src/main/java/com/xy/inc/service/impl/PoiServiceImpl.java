package com.xy.inc.service.impl;

import com.xy.inc.domain.Poi;
import com.xy.inc.repository.PoiRepository;
import com.xy.inc.service.PoiService;
import com.xy.inc.service.dto.PoiDTO;
import com.xy.inc.service.exception.PoiException;
import com.xy.inc.service.mapper.PoiMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PoiServiceImpl implements PoiService {

    private final PoiRepository poiRepository;

    private final PoiMapper poiMapper;

    public PoiServiceImpl(PoiRepository poiRepository, PoiMapper poiMapper) {
        this.poiRepository = poiRepository;
        this.poiMapper = poiMapper;
    }

    /**
     * Get all the pois.
     *
     * @return the list of PoiDTO
     */
    @Override
    @Transactional(readOnly = true)
    public List<PoiDTO> getAll() {
        return poiMapper.poiListToPoiDTOList(poiRepository.findAll());
    }

    /**
     * Save a poi.
     *
     * @param poiDTO the entity to save
     * @return the persisted entity
     * @throws
     */
    @Override
    public PoiDTO save(PoiDTO poiDTO) throws PoiException {
        if(poiDTO != null && poiDTO.getY() < 0 || poiDTO.getX() < 0) {
            throw new PoiException("Coordenadas Invalidas");
        }

        if(poiRepository.getPoiByName(poiDTO.getName()) == null) {
            Poi poi = poiRepository.save(poiMapper.poiDTOToPoi(poiDTO));
            return poiMapper.poiToPoiDTO(poi);
        }

        throw new PoiException("Registro com mesmo nome existente");
    }

    /**
     * Get all the pois in some range.
     *
     * @param max
     * @param y
     * @param x
     * @return the list of PoiDTO
     */
    @Override
    @Transactional(readOnly = true)
    public List<PoiDTO> listByProximmity(Integer x, Integer y, Integer max) {
        List<Poi> poiList = poiRepository.findAll();

        if(x > 0 && y > 0 && max > 0) {
            return poiMapper.poiListToPoiDTOList(poiList.stream()
                    .filter(poi ->
                            (pointsDistance(poi, x, y) < max)
                    ).collect(Collectors.toList()));
        }

        return new ArrayList<PoiDTO>();
    }

    private Double pointsDistance(Poi b, Integer x, Integer y) {
        Poi a = new Poi();
        a.setX(x);
        a.setY(y);

        Integer xDif = b.getX() - a.getX();
        Double xSqr = Math.pow(xDif, 2);

        Integer yDif = b.getY() - a.getY();
        Double ySqr = Math.pow(yDif, 2);

        return Math.sqrt(xSqr + ySqr);
    }

}
