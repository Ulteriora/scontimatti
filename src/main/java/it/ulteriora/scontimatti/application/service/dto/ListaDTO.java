package it.ulteriora.scontimatti.application.service.dto;


import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import it.ulteriora.scontimatti.application.domain.enumeration.StatoOrdine;

/**
 * A DTO for the Lista entity.
 */
public class ListaDTO implements Serializable {

    private Long id;

    @NotNull
    private Long progressivo;

    private Long numeroOrdine;

    private LocalDate dataOrdine;

    private BigDecimal pagamentoEffettivo;

    private BigDecimal totaleCompensare;

    private BigDecimal totaleCompentato;

    private StatoOrdine statoOrdine;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProgressivo() {
        return progressivo;
    }

    public void setProgressivo(Long progressivo) {
        this.progressivo = progressivo;
    }

    public Long getNumeroOrdine() {
        return numeroOrdine;
    }

    public void setNumeroOrdine(Long numeroOrdine) {
        this.numeroOrdine = numeroOrdine;
    }

    public LocalDate getDataOrdine() {
        return dataOrdine;
    }

    public void setDataOrdine(LocalDate dataOrdine) {
        this.dataOrdine = dataOrdine;
    }

    public BigDecimal getPagamentoEffettivo() {
        return pagamentoEffettivo;
    }

    public void setPagamentoEffettivo(BigDecimal pagamentoEffettivo) {
        this.pagamentoEffettivo = pagamentoEffettivo;
    }

    public BigDecimal getTotaleCompensare() {
        return totaleCompensare;
    }

    public void setTotaleCompensare(BigDecimal totaleCompensare) {
        this.totaleCompensare = totaleCompensare;
    }

    public BigDecimal getTotaleCompentato() {
        return totaleCompentato;
    }

    public void setTotaleCompentato(BigDecimal totaleCompentato) {
        this.totaleCompentato = totaleCompentato;
    }

    public StatoOrdine getStatoOrdine() {
        return statoOrdine;
    }

    public void setStatoOrdine(StatoOrdine statoOrdine) {
        this.statoOrdine = statoOrdine;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ListaDTO listaDTO = (ListaDTO) o;
        if(listaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), listaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ListaDTO{" +
            "id=" + getId() +
            ", progressivo=" + getProgressivo() +
            ", numeroOrdine=" + getNumeroOrdine() +
            ", dataOrdine='" + getDataOrdine() + "'" +
            ", pagamentoEffettivo=" + getPagamentoEffettivo() +
            ", totaleCompensare=" + getTotaleCompensare() +
            ", totaleCompentato=" + getTotaleCompentato() +
            ", statoOrdine='" + getStatoOrdine() + "'" +
            "}";
    }
}
