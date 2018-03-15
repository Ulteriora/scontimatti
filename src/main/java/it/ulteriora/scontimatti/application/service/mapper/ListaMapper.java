package it.ulteriora.scontimatti.application.service.mapper;

import it.ulteriora.scontimatti.application.domain.*;
import it.ulteriora.scontimatti.application.service.dto.ListaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Lista and its DTO ListaDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ListaMapper extends EntityMapper<ListaDTO, Lista> {



    default Lista fromId(Long id) {
        if (id == null) {
            return null;
        }
        Lista lista = new Lista();
        lista.setId(id);
        return lista;
    }
}
