package it.ulteriora.scontimatti.application.service.mapper;

import it.ulteriora.scontimatti.application.domain.*;
import it.ulteriora.scontimatti.application.service.dto.OrdineDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Ordine and its DTO OrdineDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface OrdineMapper extends EntityMapper<OrdineDTO, Ordine> {



    default Ordine fromId(Long id) {
        if (id == null) {
            return null;
        }
        Ordine ordine = new Ordine();
        ordine.setId(id);
        return ordine;
    }
}
