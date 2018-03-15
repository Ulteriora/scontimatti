package it.ulteriora.scontimatti.application.repository;

import it.ulteriora.scontimatti.application.domain.Lista;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Lista entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ListaRepository extends JpaRepository<Lista, Long> {

}
