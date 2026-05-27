package com.beticos.futbolapp.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = {"numero", "torneo_id"})
)
public class Fecha {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer numero;

    private LocalDate dia;

    @ManyToOne(optional = false)
    private Torneo torneo;

    public Fecha() {
    }

    public Fecha(Integer numero, LocalDate dia, Torneo torneo) {
        this.numero = numero;
        this.dia = dia;
        this.torneo = torneo;
    }


    public Long getId() {
        return id;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public LocalDate getDia() {
        return dia;
    }

    public void setDia(LocalDate dia) {
        this.dia = dia;
    }

    public Torneo getTorneo() {
        return torneo;
    }

    public void setTorneo(Torneo torneo) {
        this.torneo = torneo;
    }
}
