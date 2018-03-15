package it.ulteriora.scontimatti.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import it.ulteriora.scontimatti.application.service.OrdineService;
import it.ulteriora.scontimatti.application.web.rest.errors.BadRequestAlertException;
import it.ulteriora.scontimatti.application.web.rest.util.HeaderUtil;
import it.ulteriora.scontimatti.application.web.rest.util.PaginationUtil;
import it.ulteriora.scontimatti.application.service.dto.OrdineDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Ordine.
 */
@RestController
@RequestMapping("/api")
public class OrdineResource {

    private final Logger log = LoggerFactory.getLogger(OrdineResource.class);

    private static final String ENTITY_NAME = "ordine";

    private final OrdineService ordineService;

    public OrdineResource(OrdineService ordineService) {
        this.ordineService = ordineService;
    }

    /**
     * POST  /ordines : Create a new ordine.
     *
     * @param ordineDTO the ordineDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ordineDTO, or with status 400 (Bad Request) if the ordine has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ordines")
    @Timed
    public ResponseEntity<OrdineDTO> createOrdine(@Valid @RequestBody OrdineDTO ordineDTO) throws URISyntaxException {
        log.debug("REST request to save Ordine : {}", ordineDTO);
        if (ordineDTO.getId() != null) {
            throw new BadRequestAlertException("A new ordine cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrdineDTO result = ordineService.save(ordineDTO);
        return ResponseEntity.created(new URI("/api/ordines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ordines : Updates an existing ordine.
     *
     * @param ordineDTO the ordineDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ordineDTO,
     * or with status 400 (Bad Request) if the ordineDTO is not valid,
     * or with status 500 (Internal Server Error) if the ordineDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ordines")
    @Timed
    public ResponseEntity<OrdineDTO> updateOrdine(@Valid @RequestBody OrdineDTO ordineDTO) throws URISyntaxException {
        log.debug("REST request to update Ordine : {}", ordineDTO);
        if (ordineDTO.getId() == null) {
            return createOrdine(ordineDTO);
        }
        OrdineDTO result = ordineService.save(ordineDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ordineDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ordines : get all the ordines.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of ordines in body
     */
    @GetMapping("/ordines")
    @Timed
    public ResponseEntity<List<OrdineDTO>> getAllOrdines(Pageable pageable) {
        log.debug("REST request to get a page of Ordines");
        Page<OrdineDTO> page = ordineService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ordines");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /ordines/:id : get the "id" ordine.
     *
     * @param id the id of the ordineDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ordineDTO, or with status 404 (Not Found)
     */
    @GetMapping("/ordines/{id}")
    @Timed
    public ResponseEntity<OrdineDTO> getOrdine(@PathVariable Long id) {
        log.debug("REST request to get Ordine : {}", id);
        OrdineDTO ordineDTO = ordineService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(ordineDTO));
    }

    /**
     * DELETE  /ordines/:id : delete the "id" ordine.
     *
     * @param id the id of the ordineDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ordines/{id}")
    @Timed
    public ResponseEntity<Void> deleteOrdine(@PathVariable Long id) {
        log.debug("REST request to delete Ordine : {}", id);
        ordineService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
