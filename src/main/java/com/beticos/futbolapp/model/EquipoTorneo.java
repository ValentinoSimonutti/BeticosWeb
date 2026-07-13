package com.beticos.futbolapp.model;

import jakarta.persistence.*;

@Entity
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = {"equipo_id", "torneo_id"})
)
public class EquipoTorneo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Equipo equipo;

    @ManyToOne(optional = false)
    private Torneo torneo;

    private Integer posicion;

    private Integer partidosJugados;
    private Integer partidosGanados;
    private Integer partidosEmpatados;
    private Integer partidosPerdidos;

    private Integer golesFavor;
    private Integer golesContra;
    private Integer diferenciaGol;

    private Integer puntos;

    public EquipoTorneo() {
    }

    public EquipoTorneo(Equipo equipo, Torneo torneo) {
        this.equipo = equipo;
        this.torneo = torneo;
        this.posicion = 0;
        this.partidosJugados = 0;
        this.partidosGanados = 0;
        this.partidosEmpatados = 0;
        this.partidosPerdidos = 0;
        this.golesFavor = 0;
        this.golesContra = 0;
        this.diferenciaGol = 0;
        this.puntos = 0;
    }

    public Long getId() {
        return id;
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }

    public Torneo getTorneo() {
        return torneo;
    }

    public void setTorneo(Torneo torneo) {
        this.torneo = torneo;
    }

    public Integer getPosicion() {
        return posicion;
    }

    public void setPosicion(Integer posicion) {
        this.posicion = posicion;
    }

    public Integer getPartidosJugados() {
        return partidosJugados;
    }

    public void setPartidosJugados(Integer partidosJugados) {
        this.partidosJugados = partidosJugados;
    }

    public Integer getPartidosGanados() {
        return partidosGanados;
    }

    public void setPartidosGanados(Integer partidosGanados) {
        this.partidosGanados = partidosGanados;
    }

    public Integer getPartidosEmpatados() {
        return partidosEmpatados;
    }

    public void setPartidosEmpatados(Integer partidosEmpatados) {
        this.partidosEmpatados = partidosEmpatados;
    }

    public Integer getPartidosPerdidos() {
        return partidosPerdidos;
    }

    public void setPartidosPerdidos(Integer partidosPerdidos) {
        this.partidosPerdidos = partidosPerdidos;
    }

    public Integer getGolesFavor() {
        return golesFavor;
    }

    public void setGolesFavor(Integer golesFavor) {
        this.golesFavor = golesFavor;
    }

    public Integer getGolesContra() {
        return golesContra;
    }

    public void setGolesContra(Integer golesContra) {
        this.golesContra = golesContra;
    }

    public Integer getDiferenciaGol() {
        return diferenciaGol;
    }

    public void setDiferenciaGol(Integer diferenciaGol) {
        this.diferenciaGol = diferenciaGol;
    }

    public Integer getPuntos() {
        return puntos;
    }

    public void setPuntos(Integer puntos) {
        this.puntos = puntos;
    }
}
