package com.beticos.futbolapp.model;

import jakarta.persistence.*;

@Entity
public class Partido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int cancha;

    @ManyToOne(optional = false)
    private Torneo torneo;

    @ManyToOne(optional = false)
    private Equipo equipoLocal;

    @ManyToOne(optional = false)
    private Equipo equipoVisitante;

    private Integer golesLocal;

    private Integer golesVisitante;

    @ManyToOne(optional = false)
    private Fecha fecha;

    public Partido() {
    }

    public Partido(Fecha fecha, int cancha, Torneo torneo, Equipo equipoLocal, Equipo equipoVisitante, int golesLocal, int golesVisitante) {
        this.fecha = fecha;
        this.cancha = cancha;
        this.torneo = torneo;
        this.equipoLocal = equipoLocal;
        this.equipoVisitante = equipoVisitante;
        this.golesLocal = null;
        this.golesVisitante = null;
    }

    public Long getId() {
        return id;
    }

    public Fecha getFecha() {
        return fecha;
    }

    public void setFecha(Fecha fechaHora) {
        this.fecha = fechaHora;
    }

    public int getCancha() {
        return cancha;
    }

    public void setCancha(int cancha) {
        this.cancha = cancha;
    }

    public Torneo getTorneo() {
        return torneo;
    }

    public void setTorneo(Torneo torneo) {
        this.torneo = torneo;
    }

    public Equipo getEquipoLocal() {
        return equipoLocal;
    }

    public void setEquipoLocal(Equipo equipoLocal) {
        this.equipoLocal = equipoLocal;
    }

    public Equipo getEquipoVisitante() {
        return equipoVisitante;
    }

    public void setEquipoVisitante(Equipo equipoVisitante) {
        this.equipoVisitante = equipoVisitante;
    }

    public Integer getGolesLocal() {
        return golesLocal;
    }

    public void setGolesLocal(Integer golesLocal) {
        this.golesLocal = golesLocal;
    }

    public Integer getGolesVisitante() {
        return golesVisitante;
    }

    public void setGolesVisitante(Integer golesVisitante) {
        this.golesVisitante = golesVisitante;
    }
}
