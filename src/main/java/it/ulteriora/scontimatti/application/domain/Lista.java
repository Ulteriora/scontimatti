package it.ulteriora.scontimatti.application.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import it.ulteriora.scontimatti.application.domain.enumeration.StatoOrdine;

/**
 * A Lista.
 */
@Entity
@Table(name = "lista")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Lista implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "progressivo", nullable = false)
    private Long progressivo;

    @Column(name = "numero_ordine")
    private Long numeroOrdine;

    @Column(name = "data_ordine")
    private LocalDate dataOrdine;

    @Column(name = "pagamento_effettivo", precision=10, scale=2)
    private BigDecimal pagamentoEffettivo;

    @Column(name = "totale_compensare", precision=10, scale=2)
    private BigDecimal totaleCompensare;

    @Column(name = "totale_compentato", precision=10, scale=2)
    private BigDecimal totaleCompentato;

    @Enumerated(EnumType.STRING)
    @Column(name = "stato_ordine")
    private StatoOrdine statoOrdine;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProgressivo() {
        return progressivo;
    }

    public Lista progressivo(Long progressivo) {
        this.progressivo = progressivo;
        return this;
    }

    public void setProgressivo(Long progressivo) {
        this.progressivo = progressivo;
    }

    public Long getNumeroOrdine() {
        return numeroOrdine;
    }

    public Lista numeroOrdine(Long numeroOrdine) {
        this.numeroOrdine = numeroOrdine;
        return this;
    }

    public void setNumeroOrdine(Long numeroOrdine) {
        this.numeroOrdine = numeroOrdine;
    }

    public LocalDate getDataOrdine() {
        return dataOrdine;
    }

    public Lista dataOrdine(LocalDate dataOrdine) {
        this.dataOrdine = dataOrdine;
        return this;
    }

    public void setDataOrdine(LocalDate dataOrdine) {
        this.dataOrdine = dataOrdine;
    }

    public BigDecimal getPagamentoEffettivo() {
        return pagamentoEffettivo;
    }

    public Lista pagamentoEffettivo(BigDecimal pagamentoEffettivo) {
        this.pagamentoEffettivo = pagamentoEffettivo;
        return this;
    }

    public void setPagamentoEffettivo(BigDecimal pagamentoEffettivo) {
        this.pagamentoEffettivo = pagamentoEffettivo;
    }

    public BigDecimal getTotaleCompensare() {
        return totaleCompensare;
    }

    public Lista totaleCompensare(BigDecimal totaleCompensare) {
        this.totaleCompensare = totaleCompensare;
        return this;
    }

    public void setTotaleCompensare(BigDecimal totaleCompensare) {
        this.totaleCompensare = totaleCompensare;
    }

    public BigDecimal getTotaleCompentato() {
        return totaleCompentato;
    }

    public Lista totaleCompentato(BigDecimal totaleCompentato) {
        this.totaleCompentato = totaleCompentato;
        return this;
    }

    public void setTotaleCompentato(BigDecimal totaleCompentato) {
        this.totaleCompentato = totaleCompentato;
    }

    public StatoOrdine getStatoOrdine() {
        return statoOrdine;
    }

    public Lista statoOrdine(StatoOrdine statoOrdine) {
        this.statoOrdine = statoOrdine;
        return this;
    }

    public void setStatoOrdine(StatoOrdine statoOrdine) {
        this.statoOrdine = statoOrdine;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Lista lista = (Lista) o;
        if (lista.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), lista.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Lista{" +
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
