package it.ulteriora.scontimatti.application.service.impl;

import it.ulteriora.scontimatti.application.service.ListaService;
import it.ulteriora.scontimatti.application.domain.Lista;
import it.ulteriora.scontimatti.application.repository.ListaRepository;
import it.ulteriora.scontimatti.application.service.dto.ListaDTO;
import it.ulteriora.scontimatti.application.service.mapper.ListaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Lista.
 */
@Service
@Transactional
public class ListaServiceImpl implements ListaService {

    private final Logger log = LoggerFactory.getLogger(ListaServiceImpl.class);

    private final ListaRepository listaRepository;

    private final ListaMapper listaMapper;

    public ListaServiceImpl(ListaRepository listaRepository, ListaMapper listaMapper) {
        this.listaRepository = listaRepository;
        this.listaMapper = listaMapper;
    }

    /**
     * Save a lista.
     *
     * @param listaDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ListaDTO save(ListaDTO listaDTO) {
        log.debug("Request to save Lista : {}", listaDTO);
        Lista lista = listaMapper.toEntity(listaDTO);
        lista = listaRepository.save(lista);
        return listaMapper.toDto(lista);
    }

    /**
     * Get all the listas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ListaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Listas");
        return listaRepository.findAll(pageable)
            .map(listaMapper::toDto);
    }

    /**
     * Get one lista by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ListaDTO findOne(Long id) {
        log.debug("Request to get Lista : {}", id);
        Lista lista = listaRepository.findOne(id);
        return listaMapper.toDto(lista);
    }

    /**
     * Delete the lista by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Lista : {}", id);
        listaRepository.delete(id);
    }
}
