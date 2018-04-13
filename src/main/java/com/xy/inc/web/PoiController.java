package com.xy.inc.web;

import com.xy.inc.service.PoiService;
import com.xy.inc.service.dto.PoiDTO;
import com.xy.inc.service.exception.PoiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PoiController {

    private final Logger log = LoggerFactory.getLogger(PoiController.class);

    private static final String ENTITY_NAME = "poi";

    private final PoiService poiService;

    public PoiController (PoiService poiService) {
        this.poiService = poiService;
    }

    /**
     * GET  /pois : get all point of interest.
     *
     * @return the List<PoiDTO> with status 200 (OK) and the list of pois in body
     */
    @GetMapping("/pois")
    public ResponseEntity<List<PoiDTO>> getAll() {
        log.debug("Request to get all points of interest");
        return new ResponseEntity<>(poiService.getAll(), HttpStatus.OK);
    }

    /**
     * POST  /pois : Create a new point of interest.
     *
     * @param poiDTO the acaoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new acaoDTO, or with status 400 (Bad Request) if the pois has already an ID
     * @throws NullPointerException if the Location URI syntax is incorrect
     */
    @PostMapping("/pois")
    public ResponseEntity<PoiDTO> save(@Valid @RequestBody PoiDTO poiDTO) throws URISyntaxException {
        try{
            log.debug("Request to save point of interest: {}", poiDTO);
            PoiDTO result = poiService.save(poiDTO);
            return ResponseEntity.created(new URI("/pois/" + result.getName())).body(result);
        } catch (PoiException e) {
            log.error(e.getMessage(), e);
            poiDTO.setName(e.getMessage());
            return ResponseEntity.badRequest().header(ENTITY_NAME, e.getMessage()).body(poiDTO);
        }
    }

    /**
     * GET  /pois : get all point of interest.
     *
     * @return the List<PoiDTO> with status 200 (OK) and the list of pois in body
     */
    @GetMapping("/pois/{x}/{y}/proximmity/{max}")
    public ResponseEntity<List<PoiDTO>> listByProximmity(@PathVariable Integer x, @PathVariable Integer y, @PathVariable Integer max) {
        log.debug("Request to list all POI'S in a {} range", max);
        return new ResponseEntity<>(poiService.listByProximmity(x, y, max), HttpStatus.OK);
    }

}
