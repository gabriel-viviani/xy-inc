package com.xy.inc.service;

import com.xy.inc.service.dto.PoiDTO;
import com.xy.inc.service.exception.PoiException;

import java.util.List;

public interface PoiService {

    /**
     * Get all the pois.
     *
     * @return the list of PoiDTO
     */
    List<PoiDTO> getAll();

    /**
     * Save a poi.
     *
     * @param poiDTO the entity to save
     * @return the persisted entity
     */
    PoiDTO save (PoiDTO poiDTO) throws PoiException;

    /**
     * Get all the pois in some range
     *
     * @param max
     * @param y
     * @param x
     * @return the list of PoiDTO
     */
    List<PoiDTO> listByProximmity(Integer x, Integer y, Integer max);
}
