package it.ulteriora.scontimatti.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import it.ulteriora.scontimatti.application.service.ListaService;
import it.ulteriora.scontimatti.application.web.rest.errors.BadRequestAlertException;
import it.ulteriora.scontimatti.application.web.rest.util.HeaderUtil;
import it.ulteriora.scontimatti.application.web.rest.util.PaginationUtil;
import it.ulteriora.scontimatti.application.service.dto.ListaDTO;
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
 * REST controller for managing Lista.
 */
@RestController
@RequestMapping("/api")
public class ListaResource {

    private final Logger log = LoggerFactory.getLogger(ListaResource.class);

    private static final String ENTITY_NAME = "lista";

    private final ListaService listaService;

    public ListaResource(ListaService listaService) {
        this.listaService = listaService;
    }

    /**
     * POST  /listas : Create a new lista.
     *
     * @param listaDTO the listaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new listaDTO, or with status 400 (Bad Request) if the lista has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/listas")
    @Timed
    public ResponseEntity<ListaDTO> createLista(@Valid @RequestBody ListaDTO listaDTO) throws URISyntaxException {
        log.debug("REST request to save Lista : {}", listaDTO);
        if (listaDTO.getId() != null) {
            throw new BadRequestAlertException("A new lista cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ListaDTO result = listaService.save(listaDTO);
        return ResponseEntity.created(new URI("/api/listas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /listas : Updates an existing lista.
     *
     * @param listaDTO the listaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated listaDTO,
     * or with status 400 (Bad Request) if the listaDTO is not valid,
     * or with status 500 (Internal Server Error) if the listaDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/listas")
    @Timed
    public ResponseEntity<ListaDTO> updateLista(@Valid @RequestBody ListaDTO listaDTO) throws URISyntaxException {
        log.debug("REST request to update Lista : {}", listaDTO);
        if (listaDTO.getId() == null) {
            return createLista(listaDTO);
        }
        ListaDTO result = listaService.save(listaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, listaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /listas : get all the listas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of listas in body
     */
    @GetMapping("/listas")
    @Timed
    public ResponseEntity<List<ListaDTO>> getAllListas(Pageable pageable) {
        log.debug("REST request to get a page of Listas");
        Page<ListaDTO> page = listaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/listas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /listas/:id : get the "id" lista.
     *
     * @param id the id of the listaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the listaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/listas/{id}")
    @Timed
    public ResponseEntity<ListaDTO> getLista(@PathVariable Long id) {
        log.debug("REST request to get Lista : {}", id);
        ListaDTO listaDTO = listaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(listaDTO));
    }

    /**
     * DELETE  /listas/:id : delete the "id" lista.
     *
     * @param id the id of the listaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/listas/{id}")
    @Timed
    public ResponseEntity<Void> deleteLista(@PathVariable Long id) {
        log.debug("REST request to delete Lista : {}", id);
        listaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
