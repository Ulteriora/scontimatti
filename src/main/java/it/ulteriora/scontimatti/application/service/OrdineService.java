package it.ulteriora.scontimatti.application.service;

import it.ulteriora.scontimatti.application.service.dto.OrdineDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Ordine.
 */
public interface OrdineService {

    /**
     * Save a ordine.
     *
     * @param ordineDTO the entity to save
     * @return the persisted entity
     */
    OrdineDTO save(OrdineDTO ordineDTO);

    /**
     * Get all the ordines.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<OrdineDTO> findAll(Pageable pageable);

    /**
     * Get the "id" ordine.
     *
     * @param id the id of the entity
     * @return the entity
     */
    OrdineDTO findOne(Long id);

    /**
     * Delete the "id" ordine.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
