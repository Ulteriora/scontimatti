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
 * A Ordine.
 */
@Entity
@Table(name = "ordine")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Ordine implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "progressivo", nullable = false)
    private Long progressivo;

    @NotNull
    @Column(name = "numero_ordine", nullable = false)
    private Long numeroOrdine;

    @NotNull
    @Column(name = "data_ordine", nullable = false)
    private LocalDate dataOrdine;

    @NotNull
    @Column(name = "pagamento_effettivo", precision=10, scale=2, nullable = false)
    private BigDecimal pagamentoEffettivo;

    @Column(name = "totale_compensare", precision=10, scale=2)
    private BigDecimal totaleCompensare;

    @Column(name = "totale_compensato", precision=10, scale=2)
    private BigDecimal totaleCompensato;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "stato_ordine", nullable = false)
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

    public Ordine progressivo(Long progressivo) {
        this.progressivo = progressivo;
        return this;
    }

    public void setProgressivo(Long progressivo) {
        this.progressivo = progressivo;
    }

    public Long getNumeroOrdine() {
        return numeroOrdine;
    }

    public Ordine numeroOrdine(Long numeroOrdine) {
        this.numeroOrdine = numeroOrdine;
        return this;
    }

    public void setNumeroOrdine(Long numeroOrdine) {
        this.numeroOrdine = numeroOrdine;
    }

    public LocalDate getDataOrdine() {
        return dataOrdine;
    }

    public Ordine dataOrdine(LocalDate dataOrdine) {
        this.dataOrdine = dataOrdine;
        return this;
    }

    public void setDataOrdine(LocalDate dataOrdine) {
        this.dataOrdine = dataOrdine;
    }

    public BigDecimal getPagamentoEffettivo() {
        return pagamentoEffettivo;
    }

    public Ordine pagamentoEffettivo(BigDecimal pagamentoEffettivo) {
        this.pagamentoEffettivo = pagamentoEffettivo;
        return this;
    }

    public void setPagamentoEffettivo(BigDecimal pagamentoEffettivo) {
        this.pagamentoEffettivo = pagamentoEffettivo;
    }

    public BigDecimal getTotaleCompensare() {
        return totaleCompensare;
    }

    public Ordine totaleCompensare(BigDecimal totaleCompensare) {
        this.totaleCompensare = totaleCompensare;
        return this;
    }

    public void setTotaleCompensare(BigDecimal totaleCompensare) {
        this.totaleCompensare = totaleCompensare;
    }

    public BigDecimal getTotaleCompensato() {
        return totaleCompensato;
    }

    public Ordine totaleCompensato(BigDecimal totaleCompensato) {
        this.totaleCompensato = totaleCompensato;
        return this;
    }

    public void setTotaleCompensato(BigDecimal totaleCompensato) {
        this.totaleCompensato = totaleCompensato;
    }

    public StatoOrdine getStatoOrdine() {
        return statoOrdine;
    }

    public Ordine statoOrdine(StatoOrdine statoOrdine) {
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
        Ordine ordine = (Ordine) o;
        if (ordine.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ordine.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Ordine{" +
            "id=" + getId() +
            ", progressivo=" + getProgressivo() +
            ", numeroOrdine=" + getNumeroOrdine() +
            ", dataOrdine='" + getDataOrdine() + "'" +
            ", pagamentoEffettivo=" + getPagamentoEffettivo() +
            ", totaleCompensare=" + getTotaleCompensare() +
            ", totaleCompensato=" + getTotaleCompensato() +
            ", statoOrdine='" + getStatoOrdine() + "'" +
            "}";
    }
}
