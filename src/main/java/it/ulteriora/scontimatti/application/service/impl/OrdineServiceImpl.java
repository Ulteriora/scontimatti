package it.ulteriora.scontimatti.application.service.impl;

import it.ulteriora.scontimatti.application.service.OrdineService;
import it.ulteriora.scontimatti.application.domain.Ordine;
import it.ulteriora.scontimatti.application.repository.OrdineRepository;
import it.ulteriora.scontimatti.application.service.dto.OrdineDTO;
import it.ulteriora.scontimatti.application.service.mapper.OrdineMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Ordine.
 */
@Service
@Transactional
public class OrdineServiceImpl implements OrdineService {

    private final Logger log = LoggerFactory.getLogger(OrdineServiceImpl.class);

    private final OrdineRepository ordineRepository;

    private final OrdineMapper ordineMapper;

    public OrdineServiceImpl(OrdineRepository ordineRepository, OrdineMapper ordineMapper) {
        this.ordineRepository = ordineRepository;
        this.ordineMapper = ordineMapper;
    }

    /**
     * Save a ordine.
     *
     * @param ordineDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public OrdineDTO save(OrdineDTO ordineDTO) {
        log.debug("Request to save Ordine : {}", ordineDTO);
        Ordine ordine = ordineMapper.toEntity(ordineDTO);
        ordine = ordineRepository.save(ordine);
        return ordineMapper.toDto(ordine);
    }

    /**
     * Get all the ordines.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<OrdineDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Ordines");
        return ordineRepository.findAll(pageable)
            .map(ordineMapper::toDto);
    }

    /**
     * Get one ordine by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public OrdineDTO findOne(Long id) {
        log.debug("Request to get Ordine : {}", id);
        Ordine ordine = ordineRepository.findOne(id);
        return ordineMapper.toDto(ordine);
    }

    /**
     * Delete the ordine by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Ordine : {}", id);
        ordineRepository.delete(id);
    }
}
